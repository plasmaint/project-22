package product;

public class QuailEgg extends EggProduct {

	//Constructor
	public QuailEgg(String batchId, String productName, int packSize, double price) {
		super(batchId, productName, price);
	}

	@Override
	public String getEggType() {
		return "Quail";
	}

	@Override
	public int getPackSize() {
		return 50;
	}

}
