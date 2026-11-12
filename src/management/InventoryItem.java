package management;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import product.EggProduct;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class InventoryItem {
	// Attributes
	private final EggProduct product;
	private final IntegerProperty stockQuantity;
	private final StringProperty batchId;
	
	// Constructor
	public InventoryItem(EggProduct product, int stockQuantity, String batchId) {
		this.product = product;
		this.stockQuantity = new SimpleIntegerProperty(stockQuantity);
		this.batchId = new SimpleStringProperty(batchId);
	}
	
	 
}
