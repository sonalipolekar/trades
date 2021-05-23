package com.example.trades.configs;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.trades.services.impl.TradesServiceImpl;

public class TradesConfigTest {

	private TradesConfig trades;
	
	@BeforeEach
	public void before() {
		trades = new TradesConfig();
	}

	@Test
	public void testTradesConfig() {
		assertThat(trades.tradesSerice(null)).isInstanceOf(TradesServiceImpl.class);
	}

}
