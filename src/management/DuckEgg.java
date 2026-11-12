package management;

public class DuckEgg extends EggProduct {

	//Constructor
	public DuckEgg(String batchId, String productName, int packSize, double price) {
		super(batchId, productName, packSize, price);
	}

	@Override
	public String getEggType() {
		return "Duck";
	}
}
