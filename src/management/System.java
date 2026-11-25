package management;

import java.io.IOException;
import java.util.ArrayList;
import product.ProductList;
import transaction.TransactionHandler;
import user.Clerk;
import user.Manager;
import user.User;
import persistence.PersistenceManager;

public class System {
	// Initialize
	private ProductList productList = new ProductList();
	private Inventory inventory = new Inventory(productList);
	private TransactionHandler transactionHandler = new TransactionHandler(inventory);
	private ArrayList<User> users = new ArrayList<>();
	private User currentUser;
	private final PersistenceManager persistenceManager = new PersistenceManager();
	
	//Constructor, default accounts
	public System() {
		users.add(new Manager("john_java", "manager123", "James Gosling"));
		users.add(new Clerk("charlie_clerk", "clerk123", "Charlie Kirk"));
		users.add(new Clerk("erika_clerk", "clerk456", "Erika Kirk"));
		
		loadData();
	}
	
	// Load data
	public void loadData() {
		try {
			persistenceManager.loadAll(productList, inventory, transactionHandler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	// Save current state to CSV files
	public void saveData() {
		try {
			persistenceManager.saveAll(productList, inventory, transactionHandler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Returns the logged in user
	public User login(String username, String password) {
		for (User user : users) {
			if (user.getUsername().equals(username) && user.checkPassword(password)) {
				currentUser = user;
				return user;
			}
		}
		throw new IllegalArgumentException("Wrong username or password.");
	}
	
	public void logout() {
		saveData(); // Save
		currentUser = null;
	}
	
	// Getters
	public User getCurrentUser() {
		return currentUser;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public ProductList getProductList() {
		return productList;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public TransactionHandler getTransactionHandler() {
		return transactionHandler;
	}
	
	// Filter out the user
	public ArrayList<Clerk> getClerks() {
	    ArrayList<Clerk> clerks = new ArrayList<>();

	    for (User user : users) {
	        if (user instanceof Clerk) {
	            clerks.add((Clerk) user);
	        }
	    }
	    return clerks;
	}
}
