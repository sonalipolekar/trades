package com.example.trades.beans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @author sonalipolekar
 * 
 *         To hold trades data
 *
 */
public class AddTradesBean {

	@NotNull(message = "Trade ID cannot be null!!!")
	private String tradeId;

	@NotNull(message = "Version cannot be null!!!")
	private int version;

	@NotNull(message = "Counter Party ID cannot be null!!!")
	private String counterPartyId;

	@NotNull(message = "Book ID cannot be null!!!")
	private String bookId;

	@NotNull(message = "Maturity Date cannot be null!!!")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate maturityDate;

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

	public LocalDate getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(LocalDate maturityDate) {
		this.maturityDate = maturityDate;
	}

	@Override
	public String toString() {
		return String.format("Trade [tradeId=%s, version=%d, counterPartyId=%s, bookId=%s, maturityDate=%s]",
				this.tradeId, this.version, this.counterPartyId, this.bookId,
				this.maturityDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	}

}
