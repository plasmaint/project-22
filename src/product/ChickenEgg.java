package product;

public class ChickenEgg extends EggProduct {

	//Constructor
	public ChickenEgg(String batchId, String productName, double price, int packSize) {
		super(batchId, productName, price, packSize);
	}

	@Override
    public String getEggType() {
        return "Chicken";
    }
}
