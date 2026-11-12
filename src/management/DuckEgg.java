package management;

public class DuckEgg extends EggProduct {
	
	//Constructor
	public DuckEgg(String batchId, String productName, int packSize, double price) {
		super(batchId, productName, price);
	}

	@Override
	public String getEggType() {
		return "Duck";
	}

	@Override
	public int getPackSize() {
		if (getProductName().equalsIgnoreCase("Fresh") ||
            getProductName().equalsIgnoreCase("Salted")) {
            return 15;
        }
		
        if (getProductName().equalsIgnoreCase("Balut") ||
            getProductName().equalsIgnoreCase("Penoy")) {
            return 12;
        }
        return 0;
    }
}
