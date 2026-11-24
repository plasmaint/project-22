package main;

import controller.InventoryController;
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import management.Inventory;
import product.ProductList;


public class Main extends Application {
	@SuppressWarnings("exports")
	@Override
	public void start(Stage primaryStage) {
		try {
			// Shared data
			ProductList productList = new ProductList();
			Inventory inventory = new Inventory(productList);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/screen/LoginScreen.fxml"));
			Parent root = loader.load();
			LoginController loginController = loader.getController();
			// controller.setInventory(inventory);	// Temporarily disabled.
													// Set inventory after login instead?
			
			Scene scene = new Scene(root,1076,800);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Golden Goose Eggz Management System");
			primaryStage.show();
		} catch(Exception e) {
			e.getMessage();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
