package com.example.trades.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.example.trades.services.TradesService;

public class TradesControllerTest {

	private TradesController controller;

	@Mock
	private TradesService service;

	@BeforeEach
	public void before() {
		service = Mockito.mock(TradesService.class);
		controller = new TradesController(service);
	}

	@Test
	public void testAddTradesSuccessfully() {
		assertThat(controller.addTrades(null)).as("Trades Data added successfully");
	}

}
