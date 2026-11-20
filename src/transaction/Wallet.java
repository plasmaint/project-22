package transaction;


public class Wallet {
	// Attributes
	private double balance;
	
	// Constructor
	public Wallet(double startingBalance) {
		if (startingBalance < 0) {
			throw new IllegalArgumentException("Balance can not be negative.");
		}
		this.balance = startingBalance;
	}
	
	public void cashIn(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than 0.");
		}
		balance = balance + amount;
	}

	public void cashOut(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than 0.");
		}
		if (amount > balance) {
			throw new IllegalArgumentException("Not enough cash in the wallet.");
		}
		balance = balance - amount;
	}
	
	// Getter
	public double getBalance() {
		return balance;
	}

}