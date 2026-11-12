package product;

public class GooseEgg extends EggProduct {

	//Constructor
	public GooseEgg(String batchId, String productName, double price, int packSize) {
		super(batchId, productName, price, packSize);
	}

	@Override
	public String getEggType() {
		return "Goose";
	}
}
