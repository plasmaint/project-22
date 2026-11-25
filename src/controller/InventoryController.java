package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import management.Inventory;
import management.InventoryItem;

public class InventoryController {

    @FXML private TableView<InventoryItem> inventoryTable;
    @FXML private TableColumn<InventoryItem, String> colType;
    @FXML private TableColumn<InventoryItem, String> colProductName;
    @FXML private TableColumn<InventoryItem, Integer> colPackSize;
    @FXML private TableColumn<InventoryItem, Integer> colStock;
    @FXML private TableColumn<InventoryItem, Double> colPrice;
    @FXML private TextField amountField;
	@FXML private Label messageLabel;
	@FXML private VBox stockManagementBox;

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
    
    // Only the Manager can manage inventory, so the Clerk does not see the stock management row
    public void setRestockAllowed(boolean allowed) {
    	stockManagementBox.setVisible(allowed);
    	stockManagementBox.setManaged(allowed);
    }
    
    // *** Stock Management Methods ***
    @FXML
	private void handleAddStock() {
		InventoryItem selected = inventoryTable.getSelectionModel().getSelectedItem(); // Product selected
		if (selected == null) {
			messageLabel.setText("Select a product in the table.");
			return;
		}
 
		try {
			int amount = Integer.parseInt(amountField.getText()); // Amount input
			inventory.addStock(selected.getProduct().getProductId(), amount);
			messageLabel.setText("Added " + amount + " packs to " + selected.getProduct() + ".");
			amountField.clear();
		} catch (NumberFormatException e) {
			messageLabel.setText("Stock must be whole number.");
		} catch (IllegalArgumentException e) {
			messageLabel.setText("Invalid input.");
		}
	}
    
    @FXML
    private void handleDeductStock() {
		InventoryItem selected = inventoryTable.getSelectionModel().getSelectedItem(); // Product selected
		if (selected == null) {
			messageLabel.setText("Select a product in the table.");
			return;
		}
    	
		try {
			int amount = Integer.parseInt(amountField.getText()); // Amount input
			inventory.deductStock(selected.getProduct().getProductId(), amount);
			messageLabel.setText("Deducted " + amount + " packs from " + selected.getProduct() + ".");
			amountField.clear();
		} catch (NumberFormatException e) {
			messageLabel.setText("Stock must be whole number.");
		} catch (IllegalArgumentException e) {
			messageLabel.setText("Invalid input.");
		}
    }
    
    @FXML
    private void handleClearStock() {
		InventoryItem selected = inventoryTable.getSelectionModel().getSelectedItem(); // Product selected
		if (selected == null) {
			messageLabel.setText("Select a product in the table.");
			return;
		}
    	
		try {
			int amount = selected.getStockQuantity(); // Amount of stock
			inventory.clearStock(selected.getProduct().getProductId());
			messageLabel.setText("Cleared " + amount + " packs from " + selected.getProduct() + ".");
			amountField.clear();
		} catch (NumberFormatException e) {
			messageLabel.setText("Stock is not a whole number.");
		} catch (IllegalArgumentException e) {
			messageLabel.setText("Product already has 0 stock.");
		}
    }
}