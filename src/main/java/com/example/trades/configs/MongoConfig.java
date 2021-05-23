package com.example.trades.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.trades.repositories.impl.TradesMongoRepository;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
// enabled by default
@ConditionalOnProperty(prefix = "trades.repository.mongo", value = "enabled", havingValue = "true", matchIfMissing = true)
@Import(MongoProperties.class)
public class MongoConfig {

	private MongoProperties mongoProperties;
	
	public MongoConfig(MongoProperties mongoProperties) {
		this.mongoProperties = mongoProperties;
	}

	@Bean
	public TradesMongoRepository mongoContextStore(MongoTemplate mongoTemplate) {
		return new TradesMongoRepository(mongoTemplate);
	}
	

	@Bean
	public MongoTemplate mongoTemplate(MongoClient mongoClient) {
		return new MongoTemplate(mongoClient, mongoProperties.getDatabaseName());
	}

	@Bean
	public MongoClient mongoClient() {
		
		//mongodb://localhost:27017/mydb
		ConnectionString connection = new ConnectionString(
				"mongodb:" + mongoProperties.getHost() + mongoProperties.getDatabaseName());

		// keeping simple configuration rather than setting max/min connections,
		// socket/connection timeouts etc. for this excercise
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connection).build();

		return MongoClients.create(settings);
	}

}
