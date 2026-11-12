package management;

public class GooseEgg extends EggProduct {

	//Constructor
	public GooseEgg(String batchId, String productName, int packSize, double price) {
		super(batchId, productName, price);
	}

	@Override
	public String getEggType() {
		return "Goose";
	}

	@Override
	public int getPackSize() {
		return 6;
	}
	
}
