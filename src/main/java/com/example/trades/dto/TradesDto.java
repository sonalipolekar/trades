package com.example.trades.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 
 * @author sonalipolekar
 * 
 *         To hold trades data
 *
 */
@Document(collection = "trades")
public class TradesDto {

	@Field("Trade Id")
	private String tradeId;

	@Field("Version")
	private int version;

	@Field("Counter-Party Id")
	private String counterPartyId;

	@Field("Book-Id")
	private String bookId;

	@Field("Maturity Date")
	private String maturityDate;

	@Field("Created Date")
	private String createdDate;

	@Field("Expired")
	private String expired;

	public TradesDto(String tradeId, int version, String counterPartyId, String bookId, LocalDate maturityDate,
			LocalDate createdDate, String expired) {
		this.tradeId = tradeId;
		this.version = version;
		this.counterPartyId = counterPartyId;
		this.bookId = bookId;
		this.maturityDate = maturityDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.createdDate = createdDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.expired = expired;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCounterPartyId() {
		return counterPartyId;
	}

	public void setCounterPartyId(String counterPartyId) {
		this.counterPartyId = counterPartyId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(LocalDate maturityDate) {
		this.maturityDate = maturityDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

}
