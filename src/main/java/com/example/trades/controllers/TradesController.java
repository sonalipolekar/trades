package com.example.trades.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.trades.beans.AddTradesBean;
import com.example.trades.services.TradesService;

/**
 * 
 * @author sonalipolekar
 * 
 *         To accept Trades Request as REST services
 *
 */
@RestController
@RequestMapping("/trades")
public class TradesController {

	private TradesService tradesService;
	
	public TradesController(TradesService tradesService) {
		this.tradesService = tradesService;
	}

	@PutMapping(value = "/add")
	public ResponseEntity<String> addTrades(@RequestBody List<AddTradesBean> request) {
		this.tradesService.addTrades(request);
		return new ResponseEntity<>("Trades Data added successfully", HttpStatus.CREATED);
	}

}
