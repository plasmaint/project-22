package transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Transaction {
	// Attributes
	private String transactionId;
	private String recordedBy;
	private LocalDateTime dateTime;
	private boolean voided;
	
	
	// Constructor
	public Transaction(String transactionId, String recordedBy) {
		if (transactionId == null || transactionId.trim().equals("")) {
			throw new IllegalArgumentException("Transaction ID can not be empty.");
		}
		if (recordedBy == null || recordedBy.trim().equals("")) {
			throw new IllegalArgumentException("Recorded by can not be empty.");
		}
		this.transactionId = transactionId.trim();
		this.recordedBy = recordedBy.trim();
		this.dateTime = LocalDateTime.now();
		this.voided = false;
	}
	
	// Getters
	public String getTransactionId() {
		return transactionId;
	}

	public String getRecordedBy() {
		return recordedBy;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
	// Date and time text format
	public String getDateTimeText() {
		return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}
	
	public boolean isVoided() {
		return voided;
	}
	
	public String getStatus() {
		if (voided) {
			return "Voided";
		}
		return "Completed";
	}
	
	// Mark the transaction as voided
	public void markVoided() {
		if (voided) {
			throw new IllegalArgumentException("Transaction " + transactionId + " is already voided.");
		}
		voided = true;
	}
	
	// To be overridden
	public abstract String getType();

	public abstract double getAmount();

}
