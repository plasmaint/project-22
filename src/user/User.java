package user;

public abstract class User {
	// Attributes
	private String username;
	private String password;
	private String fullName;
	
	//Constructor
	public User(String username, String password, String fullName) {
		if (username == null || username.trim().equals("")) {
			throw new IllegalArgumentException("Username can not be empty.");
		}
		if (password == null || password.equals("")) {
			throw new IllegalArgumentException("Password can not be empty.");
		}
		if (fullName == null || fullName.trim().equals("")) {
			throw new IllegalArgumentException("Full name can not be empty.");
		}
		
		this.username = username.trim();
		this.password = password;
		this.fullName = fullName.trim();
	}
	
	// Getters (Passwords should not be publicly accessible)
		public String getUsername() {
			return username;
		}

		public String getFullName() {
			return fullName;
		}

		// Compares real password to input
		public boolean checkPassword(String input) {
			return password.equals(input);
		}

		// The Manager overrides this to return true
		public boolean isManager() {
			return false;
		}

		// Manager or Clerk
		public abstract String getRole();

		@Override
		public String toString() {
			return fullName + " (" + getRole() + ")";
		}
}
