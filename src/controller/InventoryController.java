package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import management.Inventory;
import management.InventoryItem;

public class InventoryController {

	// From FXML
    @FXML private TableView<InventoryItem> inventoryTable;
    @FXML private TableColumn<InventoryItem, String> colType;
    @FXML private TableColumn<InventoryItem, String> colProductName;
    @FXML private TableColumn<InventoryItem, Integer> colPackSize;
    @FXML private TableColumn<InventoryItem, Integer> colStock;
    @FXML private TableColumn<InventoryItem, Double> colPrice;
    @FXML private TextField amountField;
	@FXML private Label messageLabel;

	// Shared inventory
	private Inventory inventory;

	// Runs when screen loads
    @FXML
    public void initialize() {
        // Link columns to InventoryItem property names
    	colType.setCellValueFactory(new PropertyValueFactory<>("eggType"));
		colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
		colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
		colStock.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
		colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
    public void setInventory(Inventory inventory) {
    	this.inventory = inventory;
    	inventoryTable.setItems(inventory.getItems());
    }
    
    // When restock button clicked
    @FXML
	private void handleRestock() {
		InventoryItem selected = inventoryTable.getSelectionModel().getSelectedItem(); // Product selected
		if (selected == null) {
			messageLabel.setText("Select a product in the table.");
			return;
		}
 
		try {
			int amount = Integer.parseInt(amountField.getText()); // Amount input
			inventory.restock(selected.getProduct().getProductId(), amount);
			messageLabel.setText("Added " + amount + " packs to " + selected.getProduct() + ".");
			amountField.clear();
		} catch (NumberFormatException e) {
			messageLabel.setText("Stock must be whole number.");
		} catch (IllegalArgumentException e) {
			messageLabel.setText("Invalid input.");
		}
	}
}