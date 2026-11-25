package management;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import product.EggProduct;

public class InventoryItem {
	// Attributes
	private EggProduct product;
	private IntegerProperty stockQuantity;
	
	// Constructor
	public InventoryItem(EggProduct product, int stockQuantity) {
		if (product == null) {
			throw new IllegalArgumentException("Product can not be empty.");
		}
		
		if (stockQuantity < 0) {
			throw new IllegalArgumentException("Stock can not be negative.");
		}
		
		this.product = product;
		this.stockQuantity = new SimpleIntegerProperty(stockQuantity);
	}
	
	
	// Stock checker
	public boolean stockEnough(int amount) {
		return amount <= stockQuantity.get();
	}

	// Add stock
	public void addStock(int amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than 0.");
		}
		stockQuantity.set(stockQuantity.get() + amount);
	}
	
	// Deduct stock
	public void deductStock(int amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than 0.");
		}
		
		if (!stockEnough(amount)) {
			throw new IllegalArgumentException("Not enought stock: " + stockQuantity.get() + " left.");
		}
		stockQuantity.set(stockQuantity.get() - amount);
	}
	
	// Product
	public EggProduct getProduct() { return product; }
 
	
	// Stock Quantity
	public int getStockQuantity() { return stockQuantity.get(); }
	
	@SuppressWarnings("exports")
	public IntegerProperty stockQuantityProperty() { return stockQuantity; }
	
	
	// Egg Type
	public String getEggType() { return product.getEggType(); }
	
	
	// Product Name
	public String getProductName() { return product.getProductName(); }
 
	@SuppressWarnings("exports")
	public StringProperty productNameProperty() { return product.productNameProperty(); }
	
	
	// Price
	public double getPrice() { return product.getPrice(); }
 
	@SuppressWarnings("exports")
	public DoubleProperty priceProperty() { return product.priceProperty(); }
 
	
	// Pack Size
	public int getPackSize() { return product.getPackSize(); }
	
	@SuppressWarnings("exports")
	public IntegerProperty packSizeProperty() { return product.packSizeProperty(); }
}
