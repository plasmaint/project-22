package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import management.System;
import user.User;

public class MainController {

	@FXML private Label roleLabel;
    
	@FXML private Button dashboardButton;
    @FXML private Button inventoryButton;
    @FXML private Button productsButton;
    @FXML private Button salesButton;
    @FXML private Button transactionsButton;
    @FXML private Button clerkHistoryButton;

    @FXML private StackPane mainContent;

    // System from LoginController
    private System system;
    
    public void setSystem(System system) {
    	this.system = system;
    	User user = system.getCurrentUser();
    	
    	roleLabel.setText(user.getFullName() + " (" + user.getRole() + ")");
    	
    	if (!user.isManager()) {
    		hide(productsButton);
    		hide(clerkHistoryButton);
    	}
    	
    	openInventory();
    }
    
    // Hides a button
 	private void hide(Button button) {
 		button.setVisible(false);
 		button.setManaged(false);
 	}
    
    // Handles the logout process.
    @FXML
    void handleLogout() {
    	try {
			system.logout();

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/screen/LoginScreen.fxml"));
			Parent root = loader.load();
			
			LoginController loginController = loader.getController();
			loginController.setSystem(system);

			roleLabel.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    // Switches main content area to dashboard overview.
    @FXML
    void openDashboard() {
    	
    }
    
    // Switches main content area to inventory management.
    @FXML
    void openInventory() {
    	try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/screen/InventoryScreen.fxml"));
			Parent screen = loader.load();

			InventoryController inventoryController = loader.getController();
			inventoryController.setInventory(system.getInventory());
			inventoryController.setRestockAllowed(system.getCurrentUser().isManager()); // only the Manager can restock

			mainContent.getChildren().setAll(screen);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    // Switches main content area to product management.
    @FXML
    void openProducts() {
    	if (!system.getCurrentUser().isManager()) {
    		return;
    	}
    	// TODO
    }

    // Switches main content area to sales processing.
    @FXML
    void openSales() {
    	// TODO
    }

    // Switches main content area to cash flow and transaction history.
    @FXML
    void openTransactions() {
    	try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/screen/TransactionScreen.fxml"));
			Parent screen = loader.load();

			TransactionController transactionController = loader.getController();

			//mainContent.getChildren().setAll(screen);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    // Switches main content area to clerk sales history.
    @FXML
    void openClerkHistory() {
    	if (!system.getCurrentUser().isManager()) {
			return;
		}
    	// TODO
    }
}
