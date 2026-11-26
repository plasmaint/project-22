package transaction;

import java.util.ArrayList;
import management.Inventory;
import management.InventoryItem;

public class TransactionHandler {
	// Attributes
	private Inventory inventory;
	private Wallet wallet = new Wallet(0);
	private ArrayList<Transaction> transactions = new ArrayList<>();
	
	// Constructor
	public TransactionHandler(Inventory inventory) {
		this.inventory = inventory;
	}
	
	
	public String generateTransactionId() {
		return String.format("T-%04d", transactions.size() + 1);
	}
	
	public void recordSale(SaleTransaction sale) {
		// Invalid conditions
		if (sale.getItems().size() == 0) {
			throw new IllegalArgumentException("The sale has no items.");
		}
		if (getTransactionById(sale.getTransactionId()) != null) {
			throw new IllegalArgumentException("The sale is already recorded.");
		}
		
		// Check stock
		for (SaleItem item : sale.getItems()) {
			InventoryItem stock = inventory.findByProductId(item.getProduct().getProductId());
			if (stock == null) {
				throw new IllegalArgumentException(item.getProductLabel() + " not in inventory.");
			}
			if (!stock.stockEnough(item.getQuantity())) {
				throw new IllegalArgumentException("Not enough stock for " + item.getProductLabel() + ", "
				+ stock.getStockQuantity() + " left.");
			}
		}
		
		for (SaleItem item : sale.getItems()) {
			inventory.deductStock(item.getProduct().getProductId(), item.getQuantity());
		}
		wallet.cashIn(sale.getAmount());
		transactions.add(sale);
		
	}
	
	public void recordCashFlow(String transactionId, String recordedBy, String flowType, double amount, String note) {

	    if (getTransactionById(transactionId) != null) {
	        throw new IllegalArgumentException("Transaction already exists.");
	    }

	    CashFlowTransaction flow = new CashFlowTransaction(transactionId, recordedBy, flowType, amount, note);

	    if (flowType.equals("Cash In")) {
	        wallet.cashIn(amount);
	    } else {
	        wallet.cashOut(amount);
	    }

	    transactions.add(flow);

	}
	
	public void voidTransaction(String transactionId) {
		Transaction transac = getTransactionById(transactionId);
		if (transac == null) {
			throw new IllegalArgumentException("Transaction " + transactionId + " not found.");
		}
		if (transac.isVoided()) {
			throw new IllegalArgumentException("Transaction " + transactionId + " is already voided.");
		}
		transac.undoTransaction(inventory, wallet);
		transac.markVoided();
	}
	
	// Getter
	public ArrayList<Transaction> getTransactionsByUser(String userName) {
		ArrayList<Transaction> userRecord = new ArrayList<>();
		for (Transaction transac : transactions) {
			if (transac.getRecordedBy().equals(userName)) {
				userRecord.add(transac);
			}
		}
		return userRecord;
	}
	
	public Transaction getTransactionById(String transactionId) {
		for (Transaction transac : transactions) {
			if (transac.getTransactionId().equals(transactionId)) {
				return transac;
			}
		}
		return null;
	}
	
	// Gets only sales.
	public ArrayList<SaleTransaction> getSalesByUser(String username) {
	    ArrayList<SaleTransaction> userSales = new ArrayList<>();
	    for (Transaction transac : transactions) {
	        if (transac instanceof SaleTransaction 
	        		&& transac.getRecordedBy().equals(username)) {
	        	userSales.add((SaleTransaction) transac);
	        }
	    }
	    return userSales;
	}
	
	public Wallet getWallet() {
		return wallet;
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
}
