package transaction;

import product.EggProduct;

public class SaleItem {
	// Attributes
	private EggProduct product;
	private int quantity;
	private double unitPrice;
	
	// Constructor
	public SaleItem(EggProduct product, int quantity) {
		if (product == null) {
			throw new IllegalArgumentException("Product can not be empty.");
		}
		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than 0.");
		}
		this.product = product;
		this.quantity = quantity;
		this.unitPrice = product.getPrice();
	}
	
	public void addQuantity(int amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than 0.");
		}
		quantity = quantity + amount;
	}
	
	// Getter
	public double getPriceTotal() {
		return unitPrice * quantity;
	}
	
	public String getProductLabel() {
		return product.toString();
	}
	
	public EggProduct getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getUnitPrice() {
		return unitPrice;
	}
}
