package product;

public class DuckEgg extends EggProduct {
	
	//Constructor
	public DuckEgg(String batchId, String productName, double price, int packSize) {
		super(batchId, productName, price, packSize);
	}

	@Override
	public String getEggType() {
		return "Duck";
	}
}
