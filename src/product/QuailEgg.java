package product;

public class QuailEgg extends EggProduct {

	//Constructor
	public QuailEgg(String batchId, String productName, String description, double price, int packSize) {
		super(batchId, productName, description, price, packSize);
	}

	@Override
	public String getEggType() {
		return "Quail";
	}
}
