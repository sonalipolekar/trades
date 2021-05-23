package com.example.trades.repositories;

import java.util.List;

import com.example.trades.dto.TradesDto;

/**
 * 
 * @author sonalipolekar
 *
 */
public interface TradesRepository {

	public void save(TradesDto data);
	
	public void upsert(TradesDto data);
	
	public void upsertExpiry();

	public List<Integer> getTradeVersions(String tradeId);

}
