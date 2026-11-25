package product;

public class DuckEgg extends EggProduct {
	
	//Constructor
	public DuckEgg(String batchId, String productName, String description, double price, int packSize) {
		super(batchId, productName, description, price, packSize);
	}

	@Override
	public String getEggType() {
		return "Duck";
	}
}
