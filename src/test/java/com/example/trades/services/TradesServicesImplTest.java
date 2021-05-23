package com.example.trades.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import com.example.trades.beans.AddTradesBean;
import com.example.trades.repositories.TradesRepository;
import com.example.trades.services.impl.TradesServiceImpl;

public class TradesServicesImplTest {
	
	private TradesServiceImpl service;
	
	@Mock
	private TradesRepository repo;
	
	@BeforeEach
	public void before() {
		repo = Mockito.mock(TradesRepository.class);
		service = new TradesServiceImpl(repo);
	}
	
	@Test
	public void testaddTrades_withMaturityDateExpired() {
		List<AddTradesBean> request = new ArrayList<>();
		AddTradesBean bean = new AddTradesBean();
		bean.setMaturityDate(LocalDate.of(2020, 01, 02));
		request.add(bean);
		service.addTrades(request );
		verify(repo, never()).getTradeVersions(any());
	}

	
	@Test
	public void testaddTrades_withNewTradeEntry() {
		List<AddTradesBean> request = new ArrayList<>();
		AddTradesBean bean = new AddTradesBean();
		bean.setMaturityDate(LocalDate.of(2022, 01, 02));
		bean.setBookId("B1");
		bean.setCounterPartyId("CP-1");
		bean.setTradeId("T1");
		bean.setVersion(1);
		request.add(bean);
		
		service.addTrades(request );
		verify(repo, times(1)).getTradeVersions(any());
		verify(repo, times(1)).save(any());
		
	}
	
	@Test
	public void testaddTrades_withNewTradeEntry_withHigherVersion() {
		List<AddTradesBean> request = new ArrayList<>();
		AddTradesBean bean = new AddTradesBean();
		bean.setMaturityDate(LocalDate.of(2022, 01, 02));
		bean.setBookId("B1");
		bean.setCounterPartyId("CP-1");
		bean.setTradeId("T1");
		bean.setVersion(3);
		request.add(bean);
		
		List<Integer> versions = Arrays.asList(1, 2);
		when(repo.getTradeVersions("T1")).thenReturn(versions);
		service.addTrades(request );
		verify(repo, times(1)).getTradeVersions(any());
		verify(repo, times(1)).save(any());
		
	}
	
	@Test
	public void testaddTrades_withNewTradeEntry_withSameExistingVersion() {
		List<AddTradesBean> request = new ArrayList<>();
		AddTradesBean bean = new AddTradesBean();
		bean.setMaturityDate(LocalDate.of(2022, 01, 02));
		bean.setBookId("B1");
		bean.setCounterPartyId("CP-1");
		bean.setTradeId("T1");
		bean.setVersion(2);
		request.add(bean);
		
		List<Integer> versions = Arrays.asList(1, 2);
		when(repo.getTradeVersions("T1")).thenReturn(versions);
		service.addTrades(request );
		verify(repo, times(1)).getTradeVersions(any());
		verify(repo, times(1)).upsert(any());
		
	}
	
	@Test
	public void testaddTrades_withNewTradeEntry_withLowerExistingVersion() {
		List<AddTradesBean> request = new ArrayList<>();
		AddTradesBean bean = new AddTradesBean();
		bean.setMaturityDate(LocalDate.of(2022, 01, 02));
		bean.setBookId("B1");
		bean.setCounterPartyId("CP-1");
		bean.setTradeId("T1");
		bean.setVersion(1);
		request.add(bean);
		
		List<Integer> versions = Arrays.asList(1, 2);
		when(repo.getTradeVersions("T1")).thenReturn(versions);
		service.addTrades(request );
		verify(repo, times(1)).getTradeVersions(any());
		verify(repo, times(1)).upsert(any());
		
	}
	
	@Test
	public void testaddTrades_withNewTradeEntry_withLowerNonExistingVersion() {
		List<AddTradesBean> request = new ArrayList<>();
		AddTradesBean bean = new AddTradesBean();
		bean.setMaturityDate(LocalDate.of(2022, 01, 02));
		bean.setBookId("B1");
		bean.setCounterPartyId("CP-1");
		bean.setTradeId("T1");
		bean.setVersion(1);
		request.add(bean);
		
		List<Integer> versions = Arrays.asList(2, 3);
		when(repo.getTradeVersions("T1")).thenReturn(versions);
		try {
			service.addTrades(request );
			assertFalse(true);
		} catch(ResponseStatusException e) {
			assertFalse(false);
			verify(repo, times(1)).getTradeVersions(any());
			verify(repo, never()).save(any());
		}
	}
	
	@Test
	public void testUpdateTradeExpiryFlag() {
		service.updateTradeExpiryFlag();
		verify(repo, times(1)).upsertExpiry();
	}
}
