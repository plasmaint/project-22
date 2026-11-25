package user;

public class Manager extends User{
	
	// Constructor
	public Manager(String username, String password, String fullName) {
		super(username, password, fullName);
	}

	@Override
	public boolean isManager() {
		return true;
	}
	
	@Override
	public String getRole() {
		return "Manager";
	}
	
	
}
