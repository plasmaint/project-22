package management;

public class GooseEgg extends EggProduct {

	//Constructor
	public GooseEgg(String batchId, String productName, int packSize, double price) {
		super(batchId, productName, packSize, price);
	}

	@Override
	public String getEggType() {
		return "Goose";
	}
	
}
