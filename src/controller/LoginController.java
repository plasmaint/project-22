package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	// From FXML
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleChoice;
    @FXML private Button loginButton;
    @FXML private Label errorLabel;

    // Runs when screen loads
    @FXML
    public void initialize() {
        roleChoice.getItems().setAll("Manager", "Clerk"); // Set choices
        roleChoice.getSelectionModel().selectFirst(); // Defaults choice to "Manager"
    }
    
    // Handles the login process by validating inputs, authenticating user 
    // credentials, and setting respective permissions / screens.
    @FXML
    private void handleLogin() {
    	// Clear previous error messages (if any).
    	errorLabel.setText("");
    	
    	// Retrieves user inputs from input fields.
    	// Handles null values. Trims leading/trailing whitespaces (for username inputs only).
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText();
        String role = roleChoice.getValue();
        
        // Validate username.
        if (username.isBlank()) {
            errorLabel.setText("Please enter your username.");
            return;
        }
        // Validate password.
        if (password.isBlank()) {
        	errorLabel.setText("Please enter your password.");
        	return;
        }
        // Validate role selection.
        // Note: shouldn't be possible to trigger as the ComboBox defaults the choice to "Manager".
        if (role == null) {
        	errorLabel.setText("Please select a role.");
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
            MainController controller = loader.getController();

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
