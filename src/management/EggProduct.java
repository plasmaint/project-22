package management;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EggProduct {
    private final StringProperty productName;
    private final IntegerProperty stockQuantity;
    private final DoubleProperty price;
    private final StringProperty batchId;

    public EggProduct(String productName, int stockQuantity, double price, String batchId) {
        this.productName = new SimpleStringProperty(productName);
        this.stockQuantity = new SimpleIntegerProperty(stockQuantity);
        this.price = new SimpleDoubleProperty(price);
        this.batchId = new SimpleStringProperty(batchId);
    }

    // Product Name
    public String getProductName() {
        return productName.get();
    }
    public void setProductName(String productName) {
        this.productName.set(productName);
    }
    @SuppressWarnings("exports")
	public StringProperty productNameProperty() {
        return productName;
    }

    // Stock Quantity
    public int getStockQuantity() {
        return stockQuantity.get();
    }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity.set(stockQuantity);
    }
    @SuppressWarnings("exports")
	public IntegerProperty stockQuantityProperty() {
        return stockQuantity;
    }

    // Price
    public double getPrice() {
        return price.get();
    }
    public void setPrice(double price) {
        this.price.set(price);
    }
    @SuppressWarnings("exports")
	public DoubleProperty priceProperty() {
        return price;
    }

    // Batch ID
    public String getBatchId() {
        return batchId.get();
    }
    public void setBatchId(String batchId) {
        this.batchId.set(batchId);
    }
    @SuppressWarnings("exports")
	public StringProperty batchIdProperty() {
        return batchId;
    }
}