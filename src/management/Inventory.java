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
			items.add(new InventoryItem(product, 20, "B-001"));
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
	public void addNewProduct(EggProduct product, int stock, String batchId) {
		if (findByProductId(product.getProductId()) != null) {
			throw new IllegalArgumentException("Product already in the inventory");
		}
		items.add(new InventoryItem(product, stock, batchId));
	}
	
	// Add packs to an existing product
	public void restock(String productId, int amount, String batchId) {
		InventoryItem item = findByProductId(productId);
		if (item == null) {
			throw new IllegalArgumentException("Product " + productId + " not found.");
		}
		item.addStock(amount);
		item.setBatchId(batchId);
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
