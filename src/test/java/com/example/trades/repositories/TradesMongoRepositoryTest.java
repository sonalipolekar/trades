package com.example.trades.repositories;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.trades.dto.TradesDto;
import com.example.trades.repositories.impl.TradesMongoRepository;

public class TradesMongoRepositoryTest {

	private TradesMongoRepository repo;

	@Mock
	private MongoTemplate template;

	@BeforeEach
	public void before() {
		template = Mockito.mock(MongoTemplate.class);
		repo = new TradesMongoRepository(template);
	}

	@Test
	public void testSave() {
		repo.save(null);
		verify(template, times(1)).save(null);
	}

	@Test
	public void testUpsert() {
		TradesDto dto = new TradesDto("T1", 1, "CP-1", "B1", LocalDate.now(), LocalDate.now(), "N");
		repo.upsert(dto);
	}
	

	// TODO - main testing is done as part of integration due to time constraint
}
