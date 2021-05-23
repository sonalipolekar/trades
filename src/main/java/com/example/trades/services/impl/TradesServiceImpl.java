package com.example.trades.services.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.server.ResponseStatusException;

import com.example.trades.beans.AddTradesBean;
import com.example.trades.dto.TradesDto;
import com.example.trades.repositories.TradesRepository;
import com.example.trades.services.TradesService;

/**
 * 
 * @author sonalipolekar
 * 
 *         Services to handle Trades operations
 *
 */
public class TradesServiceImpl implements TradesService {

	private TradesRepository repository;

	public TradesServiceImpl(TradesRepository repository) {
		this.repository = repository;
	}

	@Override
	public void addTrades(List<AddTradesBean> request) {

		for (AddTradesBean bean : request) {

			// 1. check 'Maturity Date' is not less than today's date
			if (this.isMaturityDateNotExpired(bean.getMaturityDate())) {

				/*
				 * assumption : Any Particular Trade's(Trade ID) lower version is compared
				 * against 'Version' column i.e. if T1 has 2 entries in table with version 2 & 3
				 * and if for T1 again version 2 is received then that will be overriden and if
				 * 1 is received then it will be rejected since it is lower than version 3.
				 */

				// I have asked HR to clarify if this is correct understanding or not. However,
				// HR has not responded.
				// 2. get list of 'Versions' for specific Trade(TradeId)
				List<Integer> tradeVersions = this.repository.getTradeVersions(bean.getTradeId());
				this.store(bean, tradeVersions);
			}
		}
	}

	/**
	 * To update Trade Expiry Flag to 'N' every midnight
	 */
	@Scheduled(cron = "0 0 0 * * *")
	public void updateTradeExpiryFlag() {
		this.repository.upsertExpiry();
		System.out.println("Updated Trades Expiry Flag");
	}

	/**
	 * To store Trade data
	 * 
	 * @param bean
	 * @param tradeVersions
	 */
	private void store(AddTradesBean bean, List<Integer> tradeVersions) {
		TradesDto dto = new TradesDto(bean.getTradeId(), bean.getVersion(), bean.getCounterPartyId(), bean.getBookId(),
				bean.getMaturityDate(), LocalDate.now(), "N");

		if (tradeVersions != null && tradeVersions.size() > 0) {
			Collections.sort(tradeVersions, Comparator.reverseOrder());
			int higherVersion = tradeVersions.get(0);

			if (bean.getVersion() > higherVersion) {
				// provided new higher trade version entry data for existing TradeId so add it
				this.repository.save(dto);
			} else if (bean.getVersion() <= higherVersion && tradeVersions.contains(bean.getVersion())) {
				// provided lower or same trade existing version entry data so override it
				this.repository.upsert(dto);
			} else {
				// can log using logging framework like slf4j
				// provided lower trade non-existing version so throw an exception
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Invalid version for Trade record : " + bean.toString());
			}
		} else {
			// provided new TradeId so add it
			this.repository.save(dto);
		}
	}

	/**
	 * To compare maturity date with today's date
	 * 
	 * @param maturityDate
	 * @return true if maturity date is not older than today's date else false
	 */
	private boolean isMaturityDateNotExpired(LocalDate maturityDate) {
		if (maturityDate.isBefore(LocalDate.now()))
			return false;
		return true;
	}

}
