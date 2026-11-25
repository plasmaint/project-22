package product;

public class GooseEgg extends EggProduct {

	//Constructor
	public GooseEgg(String batchId, String productName, String description, double price, int packSize) {
		super(batchId, productName, description, price, packSize);
	}

	@Override
	public String getEggType() {
		return "Goose";
	}
}
