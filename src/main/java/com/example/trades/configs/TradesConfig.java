package com.example.trades.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.example.trades.repositories.TradesRepository;
import com.example.trades.services.TradesService;
import com.example.trades.services.impl.TradesServiceImpl;

/**
 * 
 * @author sonalipolekar
 * 
 *         To configure application's beans
 *
 */
@Configuration
@Import({ MongoConfig.class })
public class TradesConfig {
	
	/* Using Constructor based dependency injection(DI) and externaliasing Spring DI(to Spring Boot's configuration(@Configuration)).
	 * So that in future different framework can be easily replaced with
	 *   
	 */

	@Bean
	public TradesService tradesSerice(TradesRepository repository) {
		return new TradesServiceImpl(repository);
	}
	
}
