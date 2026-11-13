package product;

import java.util.ArrayList;

public class ProductList {
	private ArrayList<EggProduct> products = new ArrayList<>();
	
	public ProductList() {
		// Chicken
		products.add(new ChickenEgg("P-01", "Pewee", 210, 30));
		products.add(new ChickenEgg("P-02", "Extra Small", 240, 30));
		products.add(new ChickenEgg("P-03", "Small", 250, 30));
		products.add(new ChickenEgg("P-04", "Medium", 285, 30));
		products.add(new ChickenEgg("P-05", "Large", 320, 30));
		products.add(new ChickenEgg("P-06", "Extra Large", 340, 30));
		products.add(new ChickenEgg("P-07", "Jumbo", 365, 30));
 
		// Duck
		products.add(new DuckEgg("P-08", "Fresh", 180, 15));
		products.add(new DuckEgg("P-09", "Salted", 240, 15));
		products.add(new DuckEgg("P-10", "Balut", 210, 12));
		products.add(new DuckEgg("P-11", "Penoy", 175, 12));
 
		// Quail
		products.add(new QuailEgg("P-12", "Fresh", 135, 50));
		products.add(new QuailEgg("P-13", "Boiled", 205, 50));
 
		// Goose
		products.add(new GooseEgg("P-14", "Fresh", 440, 6));
		products.add(new GooseEgg("P-15", "Salted", 465, 6));
	}
	
	public ArrayList<EggProduct> getProducts() {
		return products;
	}
	
	// Find specific egg using Id, default products are 1-15
	public EggProduct findById(String productId) {
		for (EggProduct product : products) {
			if (product.getProductId().equals(productId)) {
				return product;
			}
		}
		return null;
	}
	
	// Add a product, can not have same Id as others
	public void addProduct(EggProduct product) {
		if (findById(product.getProductId()) != null) {
			throw new IllegalArgumentException("Product ID already exists: " + product.getProductId());
		}
		products.add(product);
	}
	
}
