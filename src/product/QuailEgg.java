package product;

public class QuailEgg extends EggProduct {

	//Constructor
	public QuailEgg(String batchId, String productName, double price, int packSize) {
		super(batchId, productName, price, packSize);
	}

	@Override
	public String getEggType() {
		return "Quail";
	}
}
