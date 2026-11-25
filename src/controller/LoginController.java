package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import management.System;

public class LoginController {

	// From FXML
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label errorLabel;
    
    // Get system from main
    private System system;
    
    public void setSystem(System system) {
		this.system = system;
	}

    // Handles the login process by validating inputs, authenticating user 
    // credentials, and setting respective permissions / screens.
    @FXML
    private void handleLogin() {
    	// Clear previous error messages (if any).
    	errorLabel.setText("");
    	
    	// Retrieves user inputs from input fields.
    	// Handles null values. Trims leading/trailing whitespaces (for username inputs only).
    	String username = usernameField.getText().trim();
		String password = passwordField.getText();
        
        // Validate username.
        if (username.isBlank() || username.equals("")) {
            errorLabel.setText("Please enter your username.");
            return;
        }
        // Validate password.
        if (password.isBlank() || username.equals("")) {
        	errorLabel.setText("Please enter your password.");
        	return;
        }
        
        
        try {
			system.login(username, password);
		} catch (IllegalArgumentException e) {
			errorLabel.setText(e.getMessage());
			return;
		}			
        // TODO: Authenticate credentials, identify user, and determine permissions.
        // TODO: Login user and switch screen to MainLayout.
        
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/screen/MainLayout.fxml")
            );

            Parent root = loader.load();

            // Retrieve the MainController.
            MainController mainController = loader.getController();
            // mainController.setSystem(system);

            // TODO: Pass the authenticated User to MainController.

            // Replace the current screen.
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();

        } catch (IOException e) {
            errorLabel.setText("Unable to load the main screen.");
            e.printStackTrace();
        }
    }
}
