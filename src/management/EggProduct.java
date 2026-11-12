package management;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public abstract class EggProduct {
	private final StringProperty batchId;
    private final StringProperty productName;
    private final IntegerProperty packSize;
    private final DoubleProperty price;
    
    // Constructor
    public EggProduct(String batchId, String productName, int packSize, double price) {
    	this.batchId = new SimpleStringProperty(batchId);
        this.productName = new SimpleStringProperty(productName);
        this.packSize = new SimpleIntegerProperty(packSize);
        this.price = new SimpleDoubleProperty(price);
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
    
    // Pack Size
    public int getPackSize() {
        return packSize.get();
    }

    public void setPackSize(int packSize) {
        this.packSize.set(packSize);
    }
    @SuppressWarnings("exports")
    public IntegerProperty packSizeProperty() {
        return packSize;
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

    // Type
    public abstract String getEggType();
}