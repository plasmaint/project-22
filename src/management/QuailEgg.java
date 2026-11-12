package management;

public class QuailEgg extends EggProduct {

	//Constructor
	public QuailEgg(String batchId, String productName, int packSize, double price) {
		super(batchId, productName, packSize, price);
	}

	@Override
	public String getEggType() {
		return "Quail";
	}

}
