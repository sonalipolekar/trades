package com.example.trades.repositories.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.example.trades.dto.TradesDto;
import com.example.trades.repositories.TradesRepository;

/**
 * 
 * @author Mongo Repository to store Trades Data
 *
 */
public class TradesMongoRepository implements TradesRepository {

	private MongoTemplate mongoTemplate;

	public TradesMongoRepository(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void save(TradesDto data) {
		this.mongoTemplate.save(data);
	}

	@Override
	public void upsert(TradesDto data) {
		Query query = new Query();
		query.addCriteria(Criteria.where("Trade Id").is(data.getTradeId()));

		Update update = new Update();
		update.set("Trade Id", data.getTradeId());
		update.set("Version", data.getVersion());
		update.set("Book-Id", data.getBookId());
		update.set("Counter-Party Id", data.getCounterPartyId());
		update.set("Created Date", data.getCreatedDate());
		update.set("Expired", data.getExpired());
		update.set("Maturity Date", data.getMaturityDate());

		this.mongoTemplate.upsert(query, update, TradesDto.class);

	}

	@Override
	public List<Integer> getTradeVersions(String tradeId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("Trade Id").is(tradeId));
		query.fields().include("Version");

		try {
			return this.mongoTemplate.findDistinct(query, "Version", TradesDto.class, Integer.class);
		} catch (Exception e) {
			// Logger is better option but for this exercise using this
			System.out.println("failed!!!!!!!!!!!!!" + e.getLocalizedMessage());
		}
		return null;
	}

	@Override
	public void upsertExpiry() {
		Query query = new Query();
		LocalDate now = LocalDate.now();
		query.addCriteria(Criteria.where("Maturity Date").lt(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

		Update update = new Update();
		update.set("Expired", "Y");

		BulkOperations ops = this.mongoTemplate.bulkOps(BulkMode.UNORDERED, TradesDto.class);
		ops.updateMulti(query, update);

		ops.execute();

	}

}
