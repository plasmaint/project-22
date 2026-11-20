package transaction;

import java.util.ArrayList;
import product.EggProduct;

public class SaleTransaction extends Transaction {
	// Attributes
	private ArrayList<SaleItem> items = new ArrayList<>();

	// Constructor
	public SaleTransaction(String transactionId, String recordedBy) {
		super(transactionId, recordedBy);
	}


	// Adds product, if already in, only quantity is increased.
	@SuppressWarnings("exports")
	public void addItem(EggProduct product, int quantity) {
		if (product == null) {
			throw new IllegalArgumentException("Product can not be empty.");
		}
		for (SaleItem item : items) {
			if (item.getProduct().getProductId().equals(product.getProductId())) {
				item.addQuantity(quantity);
				return;
			}
		}
		items.add(new SaleItem(product, quantity));
	}
	
	// Getter
	public ArrayList<SaleItem> getItems() {
		return items;
	}

	@Override
	public String getType() {
		return "Sale";
	}

	// Total of all the lines
	@Override
	public double getAmount() {
		double total = 0;
		for (SaleItem item : items) {
			total = total + item.getPriceTotal();
		}
		return total;
	}

}