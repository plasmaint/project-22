package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import management.Inventory;
import product.ChickenEgg;
import product.DuckEgg;
import product.EggProduct;
import product.GooseEgg;
import product.ProductList;
import product.QuailEgg;

public class ProductController {

	@FXML private Label productsUniqueAmount;
	@FXML private TableView<EggProduct> productsTable;
	@FXML private TableColumn<EggProduct, String> colProductId;
	@FXML private TableColumn<EggProduct, String> colType;
	@FXML private TableColumn<EggProduct, String> colName;
    @FXML private TableColumn<EggProduct, String> colDescription;
    @FXML private TableColumn<EggProduct, Double> colPrice;
    @FXML private TableColumn<EggProduct, Integer> colPackSize;
    @FXML private VBox productManagementBox;
    @FXML private Label statusMessageLabel;

    // Adding/Editing
    @FXML private ComboBox<String> typeComboBox;
	@FXML private TextField nameField;
	@FXML private TextField descriptionField;
	@FXML private TextField priceField;
	@FXML private TextField packSizeField;
	
	// Shared products list
	private ProductList productList;
	private Inventory inventory;
    
	// Runs when screen loads
    @FXML
    public void initialize() {
        // Link columns to EggProduct property names
    	colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
    	colType.setCellValueFactory(new PropertyValueFactory<>("eggType"));
		colName.setCellValueFactory(new PropertyValueFactory<>("productName"));
		colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
		
		typeComboBox.getItems().addAll("Chicken", "Duck", "Goose", "Quail");
    }
    
    public void setProducts(ProductList productList, Inventory inventory) {
    	this.productList = productList;
		this.inventory = inventory;
		productsTable.setItems(productList.getProducts());
		updateCount();
    }
    
    // Shows how many products there are
 	private void updateCount() {
 		productsUniqueAmount.setText("Unique Products: " + productList.getProducts().size());
 	}
 	
 	// Builds the right kind of egg product
 	private EggProduct createProduct(String type, String id, String name, String description, double price, int packSize) {
 		if (type == null) {
 			throw new IllegalArgumentException("Select an egg type.");
 		}
 		if (type.equals("Chicken")) {
 			return new ChickenEgg(id, name, description, price, packSize);
 		} else if (type.equals("Duck")) {
 			return new DuckEgg(id, name, description, price, packSize);
 		} else if (type.equals("Goose")) {
 			return new GooseEgg(id, name, description, price, packSize);
 		} else if (type.equals("Quail")) {
 			return new QuailEgg(id, name, description, price, packSize);
 		}
 		throw new IllegalArgumentException("Unknown egg type: " + type);
 	}
 	
 	// Empties the input fields
 	private void clearFields() {
 		typeComboBox.setValue(null);
 		nameField.clear();
 		descriptionField.clear();
 		priceField.clear();
 		packSizeField.clear();
 	}
 	
 	// Copy selected product values into field
 	@FXML
	void handleRowClicked() {
		EggProduct selected = productsTable.getSelectionModel().getSelectedItem();
		if (selected == null) {
			return;
		}
		typeComboBox.setValue(selected.getEggType());
		nameField.setText(selected.getProductName());
		descriptionField.setText(selected.getDescription());
		priceField.setText(String.valueOf(selected.getPrice()));
		packSizeField.setText(String.valueOf(selected.getPackSize()));
		statusMessageLabel.setText("Editing " + selected + ". Change the fields.");
	}
    
    @FXML
    void handleAddNewProduct() {
    	try {
			double price = Double.parseDouble(priceField.getText().trim());
			int packSize = Integer.parseInt(packSizeField.getText().trim());

			// The constructor rejects a blank name, blank description, negative price
			EggProduct product = createProduct(typeComboBox.getValue(), productList.generateProductId(),
					nameField.getText(), descriptionField.getText(), price, packSize);

			productList.addProduct(product);
			inventory.addNewProduct(product, 0); // new products start with no stock

			clearFields();
			updateCount();
			statusMessageLabel.setText("Added " + product + " (" + product.getProductId() + ").");
		} catch (NumberFormatException e) {
			statusMessageLabel.setText("Price and pack size must be numbers.");
		} catch (IllegalArgumentException e) {
			statusMessageLabel.setText(e.getMessage());
		}

    }

    @FXML
    void handleEditExistingProduct() {
    	EggProduct selected = productsTable.getSelectionModel().getSelectedItem(); // Product selected
		if (selected == null) {
			statusMessageLabel.setText("Select a product in the table.");
			return;
		}

		try {
			if (typeComboBox.getValue() != null && !typeComboBox.getValue().equals(selected.getEggType())) {
				throw new IllegalArgumentException("The egg type can not be changed.");
			}

			String name = nameField.getText();
			String description = descriptionField.getText();
			double price = Double.parseDouble(priceField.getText().trim());
			int packSize = Integer.parseInt(packSizeField.getText().trim());

			createProduct(selected.getEggType(), selected.getProductId(), name, description, price, packSize);

			selected.setProductName(name);
			selected.setDescription(description);
			selected.setPrice(price);
			selected.setPackSize(packSize);

			statusMessageLabel.setText("Updated " + selected + ".");
		} catch (NumberFormatException e) {
			statusMessageLabel.setText("Price and pack size must be numbers.");
		} catch (IllegalArgumentException e) {
			statusMessageLabel.setText(e.getMessage());
		}
    }
    
}
