package management;

import java.util.ArrayList;
import java.util.Map;
import transaction.Transaction;

public abstract class Dashboard {
	
	// Attributes
	protected final int LOW_STOCK_LEVEL = 5;
	protected System system;
	
	// Constructor
	public Dashboard(System system) {
		if (system == null) {
			throw new IllegalArgumentException("System can not be empty.");
		}
		this.system = system;
	}
	
	// Number of completed transactions
	protected int countCompleted(ArrayList<Transaction> records) {
		int count = 0;
		for (Transaction transac : records) {
			if (!transac.isVoided()) {
				count++;
			}
		}
		return count;
	}
	
	// Number of voided transactions
	protected int countVoided(ArrayList<Transaction> records) {
		return records.size() - countCompleted(records);
	}
	
	// Total Amount from completed transactions
	protected double totalCompleted(ArrayList<Transaction> records) {
		double total = 0;
		for (Transaction transac : records) {
			if (!transac.isVoided()) {
				total = total + transac.getAmount();
			}
		}
		return total;
	}
	
	// Count of products low in stock
	protected int countLowStock() {
		int count = 0;
		for (InventoryItem item : system.getInventory().getItems()) {
			if (item.getStockQuantity() <= LOW_STOCK_LEVEL) {
				count++;
			}
		}
		return count;
	}

	// Peso format
	protected String peso(double amount) {
		return String.format("₱%,.2f", amount);
	}
	
	// Getters
	public abstract Map<String, String> getStats();
	
	public abstract ArrayList<Transaction> getTransactions();
	
	
}
