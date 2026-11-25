package management;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import product.EggProduct;
import product.ProductList;

public class Inventory {
	private ObservableList<InventoryItem> items = FXCollections.observableArrayList();
	
	@SuppressWarnings("exports")
	public Inventory(ProductList products) {
		for (EggProduct product : products.getProducts()) {
			items.add(new InventoryItem(product, 20));
		}
	}
	
	public ObservableList<InventoryItem> getItems() {
		return items;
	}
	// Find item using ID
	public InventoryItem findByProductId(String productId) {
		for (InventoryItem item : items) {
			if (item.getProduct().getProductId().equals(productId)) {
				return item;
			}
		}
		return null;
	}
	
	// Stock
	public int getStock(String productId) {
		InventoryItem item = findByProductId(productId);
		if (item == null) {
			throw new IllegalArgumentException("Product " + productId + " not found.");
		}
		return item.getStockQuantity();
	}
	
	// Add new product
	@SuppressWarnings("exports")
	public void addNewProduct(EggProduct product, int stock) {
		if (findByProductId(product.getProductId()) != null) {
			throw new IllegalArgumentException("Product already in the inventory");
		}
		items.add(new InventoryItem(product, stock));
	}
	
	// Add packs to an existing product
	public void restock(String productId, int amount) {
		InventoryItem item = findByProductId(productId);
		if (item == null) {
			throw new IllegalArgumentException("Product " + productId + " not found.");
		}
		item.addStock(amount);
	}
 
	// Take packs out of stock
	public void removeStock(String productId, int amount) {
		InventoryItem item = findByProductId(productId);
		if (item == null) {
			throw new IllegalArgumentException("Product " + productId + " not found.");
		}
		item.deductStock(amount);
	}
	
	
	
}
