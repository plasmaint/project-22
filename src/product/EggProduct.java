package product;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public abstract class EggProduct {
	// Attributes
	private StringProperty productId;
    private StringProperty productName;
    private DoubleProperty price;
    private IntegerProperty packSize;
    
    // Constructor
    public EggProduct(String productId, String productName, double price, int packSize) {
    	if (productId == null || productId.trim().equals("")) {
            throw new IllegalArgumentException("Product ID can not be empty.");
        }
        if (productName == null || productName.trim().equals("")) {
            throw new IllegalArgumentException("Product name can not be empty.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price can not be negative.");
        }
        if (packSize <= 0) {
            throw new IllegalArgumentException("Pack size must be more than 0.");
        }
        
    	this.productId = new SimpleStringProperty(productId);
        this.productName = new SimpleStringProperty(productName);
        this.price = new SimpleDoubleProperty(price);
        this.packSize = new SimpleIntegerProperty(packSize);
    }
    
    // Product ID
    public String getProductId() {
        return productId.get();
    }
    
    public StringProperty productIdProperty() {
    	return productId;
    }

    // Product Name
    public String getProductName() {
        return productName.get();
    }
    
    public void setProductName(String productName) {
    	if (productName == null || productName.trim().equals("")) {
            throw new IllegalArgumentException("Product name can not be empty.");
        }
        this.productName.set(productName.trim());
    }
    
	public StringProperty productNameProperty() {
        return productName;
    }

    // Price
    public double getPrice() {
        return price.get();
    }
    public void setPrice(double price) {
    	if (price < 0) {
    		throw new IllegalArgumentException("Price can not be negative.");
    	}
        this.price.set(price);
    }
    
	public DoubleProperty priceProperty() {
        return price;
    }
	
	//Pack Size
	public int getPackSize() {
		return packSize.get();
	}
	
	public void setPackSize(int packSize) {
		if (packSize <= 0) {
			throw new IllegalArgumentException("Pack size must be more than 0.");
		}
		this.packSize.set(packSize);
	}
	
	public IntegerProperty packSizeProperty() {
		return packSize;
	}

    // Type
    public abstract String getEggType();
    
    //Egg type with variant
    @Override
    public String toString() {
    	return getEggType() + " (" + getProductName() + ")";
    }
}