package management;

public class ChickenEgg extends EggProduct {

	//Constructor
	public ChickenEgg(String batchId, String productName, int packSize, double price) {
		super(batchId, productName, packSize, price);
	}

	@Override
    public String getEggType() {
        return "Chicken";
    }
}
