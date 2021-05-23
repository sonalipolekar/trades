package com.example.trades.configs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.trades.repositories.impl.TradesMongoRepository;
import com.mongodb.client.MongoClient;

public class MongoConfigTest {
	
	private MongoConfig config;
	
	@Mock
	private MongoProperties props;
	
	@Mock
	private MongoClient client;
	
	@BeforeEach
	public void before() {
		props = Mockito.mock(MongoProperties.class);
		client = Mockito.mock(MongoClient.class);
		config = new MongoConfig(props);
	}
	
	@Test
	public void testMongoConfig() {
		when(props.getDatabaseName()).thenReturn("myTestDb");
		when(props.getHost()).thenReturn("//localhost:1234/");
		assertThat(config.mongoClient()).isInstanceOf(MongoClient.class);
		assertThat(config.mongoContextStore(null)).isInstanceOf(TradesMongoRepository.class);
		assertThat(config.mongoTemplate(client)).isInstanceOf(MongoTemplate.class);
	}

}
