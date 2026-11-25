package main;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import management.System;


public class Main extends Application {
	@SuppressWarnings("exports")
	@Override
	public void start(Stage primaryStage) {
		try {
			// Shared data
			System system = new System();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/screen/LoginScreen.fxml"));
			Parent root = loader.load();
			LoginController loginController = loader.getController();
			loginController.setSystem(system);
			
			Scene scene = new Scene(root, 1076, 800);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Golden Goose Eggz Management System");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
