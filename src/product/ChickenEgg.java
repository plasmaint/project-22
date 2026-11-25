package product;

public class ChickenEgg extends EggProduct {

	//Constructor
	public ChickenEgg(String batchId, String productName, String description, double price, int packSize) {
		super(batchId, productName, description, price, packSize);
	}

	@Override
    public String getEggType() {
        return "Chicken";
    }
}
