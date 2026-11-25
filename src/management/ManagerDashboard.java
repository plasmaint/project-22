package management;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import transaction.Transaction;
import user.Clerk;

public class ManagerDashboard extends Dashboard {


	//Constructor
	public ManagerDashboard(System system) {
		super(system);
	}

	@Override
	public Map<String, String> getStats() {
		ArrayList<Transaction> records = getTransactions();
        Map<String, String> stats = new LinkedHashMap<>();

        stats.put("Wallet Balance", peso(system.getTransactionHandler().getWallet().getBalance()));
        stats.put("Total Sales", peso(totalCompleted(records)));
        stats.put("Sales Recorded", String.valueOf(countCompleted(records)));
        stats.put("Voided", String.valueOf(countVoided(records)));
        stats.put("Low-Stock Products", String.valueOf(countLowStock()));
        stats.put("Clerks", String.valueOf(system.getClerks().size()));

        return stats;
	}

	
	@Override
	public ArrayList<Transaction> getTransactions() {
		return system.getTransactionHandler().getTransactions();
	}

	// Sales history of one clerk
	public ArrayList<Transaction> getSalesOf(Clerk clerk) {
		return system.getTransactionHandler().getTransactionsByUser(clerk.getUsername());
	}
	// Stats of one clerk
	public Map<String, String> getClerkStats(Clerk clerk) {

	    ArrayList<Transaction> records = getSalesOf(clerk);

	    Map<String, String> stats = new LinkedHashMap<>();

	    stats.put("Clerk Name", clerk.getFullName());
	    stats.put("Total Sales", peso(totalCompleted(records)));
	    stats.put("Sales Recorded", String.valueOf(countCompleted(records)));
	    stats.put("Voided", String.valueOf(countVoided(records)));

	    return stats;
	}
	
	@Override
	public String getTitle() {
		return "Manager Dashboard";
	}

	@Override
	public String getSubtitle() {
		return "Overview of Management";
	}

}
