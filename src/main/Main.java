package main;
	
import controller.InventoryController;
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
			
			// Load screen
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/screen/InventoryScreen.fxml"));
			Parent root = loader.load();
			InventoryController controller = loader.getController();
			controller.setInventory(inventory);
			
			Scene scene = new Scene(root,520,400);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Egg Inventory");
			primaryStage.show();
		} catch(Exception e) {
			e.getMessage();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
