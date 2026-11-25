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
import management.System;
import management.Dashboard;
import management.ManagerDashboard;
import management.ClerkDashboard;
import user.Clerk;
import user.User;

public class MainController {

	@FXML private Label roleLabel;
    
	@FXML private Button dashboardButton;
    @FXML private Button inventoryButton;
    @FXML private Button productsButton;
    @FXML private Button transactionsButton;
    @FXML private Button clerkSalesButton;

    @FXML private StackPane mainContent;

    // System from LoginController
    private System system;
    
    public void setSystem(System system) {
    	this.system = system;
    	User user = system.getCurrentUser();
    	
    	roleLabel.setText(user.getFullName() + " (" + user.getRole() + ")");
    	
    	if (!user.isManager()) {
    		hide(productsButton);
    		hide(clerkSalesButton);
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
    		// Clear the logged-in user
			system.logout();

        	// Retrieve login screen and give it the system again.
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/screen/LoginScreen.fxml"));
			Parent root = loader.load();
			
			LoginController loginController = loader.getController();
			loginController.setSystem(system);

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
            	InventoryController inventoryController = (InventoryController) childController;
            	inventoryController.setInventory(system.getInventory());
            	inventoryController.setRestockAllowed(system.getCurrentUser().isManager()); // only the Manager can restock
            } else if (childController instanceof ProductController) {
            	ProductController productController = (ProductController) childController;
            	productController.setProducts(system.getProductList());
            } else if (childController instanceof DashboardController) {

                DashboardController dashboardController = (DashboardController) childController;

                if (system.getCurrentUser().isManager()) {

                    dashboardController.setDashboard(new ManagerDashboard(system));
                } else {
                    Clerk clerk = (Clerk) system.getCurrentUser();

                    dashboardController.setDashboard(new ClerkDashboard(system, clerk));
                }
            }
            
            // Replace main content area with respective screen.
            mainContent.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Switches main content area to respective content screens.
    @FXML void openDashboard() { loadContent("DashboardScreen.fxml");    }
    @FXML void openInventory() { loadContent("InventoryScreen.fxml");    }    
    @FXML void openTransactions() { loadContent("TransactionScreen.fxml");  }
    
    // Manager only screens
    @FXML void openProducts() { 
    	if (!system.getCurrentUser().isManager()) {
    		return;
    	}
    	loadContent("ProductScreen.fxml");
    }
    @FXML void openClerkSales() { 
    	if (!system.getCurrentUser().isManager()) {
    		return;
    	}
    	loadContent("ClerkSalesScreen.fxml");
    }
}
    