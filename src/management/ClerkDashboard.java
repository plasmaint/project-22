package management;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import transaction.Transaction;
import user.Clerk;

public class ClerkDashboard extends Dashboard {
	
	// Attributes
	private Clerk clerk;

	// Constructor
	public ClerkDashboard(System system, Clerk clerk) {
		super(system);
		if (clerk == null) {
			throw new IllegalArgumentException("Clerk can not be null.");
		}
		this.clerk = clerk;
	}

	@Override
	public Map<String, String> getStats() {
		ArrayList<Transaction> records = getTransactions();
		Map<String, String> stats = new LinkedHashMap<>();
		
		stats.put("My Sales", String.valueOf(countCompleted(records)));
		stats.put("My Total Sales", peso(totalCompleted(records)));
		stats.put("Voided", String.valueOf(countVoided(records)));
		stats.put("Low-Stock Products", String.valueOf(countLowStock()));
		
		return stats;
	}

	@Override
	public ArrayList<Transaction> getTransactions() {
		return system.getTransactionHandler().getTransactionsByUser(clerk.getUsername());
	}
	
	@Override
	public String getTitle() {
		return "Clerk Dashboard";
	}

	@Override
	public String getSubtitle() {
		return clerk.getFullName() + " (" + clerk.getUsername() + ")";
	}

}
