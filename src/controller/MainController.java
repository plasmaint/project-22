package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MainController {

	@FXML private Label roleLabel;
    
	@FXML private Button dashboardButton;
    @FXML private Button inventoryButton;
    @FXML private Button productsButton;
    @FXML private Button salesButton;
    @FXML private Button transactionsButton;
    @FXML private Button clerkHistoryButton;

    @FXML private StackPane contentArea;

    // Handles the logout process.
    @FXML
    void handleLogout() {

    }

    // Switches main content area to dashboard overview.
    @FXML
    void openDashboard() {
    	
    }
    
    // Switches main content area to inventory management.
    @FXML
    void openInventory() {
    	
    }
    
    // Switches main content area to product management.
    @FXML
    void openProducts() {
    	
    }

    // Switches main content area to sales processing.
    @FXML
    void openSales() {

    }

    // Switches main content area to cash flow and transaction history.
    @FXML
    void openTransactions() {

    }

    // Switches main content area to clerk sales history.
    @FXML
    void openClerkHistory() {
    	
    }
}
