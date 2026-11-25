package user;

public class Clerk extends User{
	
	//Constructor
	public Clerk(String username, String password, String fullName) {
		super(username, password, fullName);
	}
	
	@Override
	public String getRole() {
		return "Clerk";
	}
	
}
