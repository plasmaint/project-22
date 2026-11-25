package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import management.Inventory;
import product.ProductList;

public class MainController {

	@FXML private Label roleLabel;
    
	@FXML private Button dashboardButton;
    @FXML private Button inventoryButton;
    @FXML private Button productsButton;
    @FXML private Button transactionsButton;
    @FXML private Button clerkSalesButton;

    @FXML private StackPane mainContent;

    // Handles the logout process.
    @FXML
    void handleLogout() {
        try {
        	// Retrieve login screen.
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/screen/LoginScreen.fxml"));
            Parent root = loader.load();

            // Get dimensions of current screen.
            Stage stage = (Stage) roleLabel.getScene().getWindow();
            double width = stage.getWidth();
            double height = stage.getHeight();
            
            // Load login screen using current screen dimensions.
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper function. Handles the loading of the screens.
    private void loadContent(String screenFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/screen/" + screenFile));
            Parent root = loader.load();
            
            Object childController = loader.getController();
            
            if (childController instanceof InventoryController) {
            	// TODO: set/load inventory
            } else if (childController instanceof ProductController) {
            	// TODO: set/load products
            }
            
            // Replace main content area with respective screen.
            mainContent.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Switches main content area to respective content screens.
    @FXML void openDashboard()    { loadContent("DashboardScreen.fxml");    }
    @FXML void openInventory()    { loadContent("InventoryScreen.fxml");    }    
    @FXML void openProducts()     { loadContent("ProductScreen.fxml");      }
    @FXML void openTransactions() { loadContent("TransactionScreen.fxml");  }
    @FXML void openClerkSales()   { loadContent("ClerkSalesScreen.fxml");   }
}
