package management;

import java.util.ArrayList;
import product.ProductList;
import transaction.TransactionHandler;
import user.Clerk;
import user.Manager;
import user.User;

public class System {
	// Initialize
	private ProductList productList = new ProductList();
	private Inventory inventory = new Inventory(productList);
	private TransactionHandler transactionHandler = new TransactionHandler(inventory);
	private ArrayList<User> users = new ArrayList<>();
	private User currentUser;
	
	//Constructor, default accounts
	public System() {
		users.add(new Manager("john_java", "manager123", "James Gosling"));
		users.add(new Clerk("charlie_clerk", "clerk123", "Charlie Kirk"));
		users.add(new Clerk("erika_clerk", "clerk456", "Erika Kirk"));
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
}
