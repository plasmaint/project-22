package product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductList {
	private ObservableList<EggProduct> products = FXCollections.observableArrayList();
	
	public ProductList() {
		// Chicken
		products.add(new ChickenEgg("P-01", "Pewee", "41-45g smallest eggs", 210, 30));
		products.add(new ChickenEgg("P-02", "Extra Small", "46-50g small eggs", 240, 30));
		products.add(new ChickenEgg("P-03", "Small", "51-55g good for frying", 250, 30));
		products.add(new ChickenEgg("P-04", "Medium", "56-60g most common size", 285, 30));
		products.add(new ChickenEgg("P-05", "Large", "61-65g good for baking", 320, 30));
		products.add(new ChickenEgg("P-06", "Extra Large", "66-70g for bulk cooking", 340, 30));
		products.add(new ChickenEgg("P-07", "Jumbo", "Over 70g, largest eggs", 365, 30));
 
		// Duck
		products.add(new DuckEgg("P-08", "Fresh", "Thick-shelled eggs, rich yolk", 180, 15));
		products.add(new DuckEgg("P-09", "Salted", "Brined eggs, ready to eat", 240, 15));
		products.add(new DuckEgg("P-10", "Balut", "Fertilized eggs, boiled", 210, 12));
		products.add(new DuckEgg("P-11", "Penoy", "Undeveloped embryo, boiled", 175, 12));
 
		// Quail
		products.add(new QuailEgg("P-12", "Fresh", "Small, speckled eggs", 135, 50));
		products.add(new QuailEgg("P-13", "Boiled", "Ready to use for kwek kwek, etc.", 205, 50));
 
		// Goose
		products.add(new GooseEgg("P-14", "Fresh", "Extra-large eggs, premium", 440, 6));
		products.add(new GooseEgg("P-15", "Salted", "Brined eggs", 465, 6));
	}
	
	public ObservableList<EggProduct> getProducts() {
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
	
	// Makes the next free ID
	public String generateProductId() {
		int highest = 0;
		for (EggProduct product : products) {
			String id = product.getProductId();
			if (id.startsWith("P-")) {
				try {
					int number = Integer.parseInt(id.substring(2));
					if (number > highest) {
						highest = number;
					}
				} catch (NumberFormatException e) {
					// not a generated ID
				}
			}
		}
		return String.format("P-%02d", highest + 1);
	}
	
	// Add a product, can not have same Id as others
	public void addProduct(EggProduct product) {
		if (findById(product.getProductId()) != null) {
			throw new IllegalArgumentException("Product ID already exists: " + product.getProductId());
		}
		products.add(product);
	}
	
}
