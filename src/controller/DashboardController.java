package controller;

import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import management.Dashboard;
import management.ManagerDashboard;
import user.Clerk;

public class DashboardController {
	
    @FXML private Label titleLabel;
    @FXML private Label subtitleLabel;
    @FXML private VBox statsContainer;
    @FXML private VBox clerkSection;
    @FXML private VBox clerkStatsContainer;
    @FXML private ComboBox<Clerk> clerkSelector;

    // Receives the appropriate dashboard
    public void setDashboard(Dashboard dashboard) {
        titleLabel.setText(dashboard.getTitle());
        subtitleLabel.setText(dashboard.getSubtitle());

        // Display general statistics
        displayStats(statsContainer, dashboard.getStats());

        // Manager dashboard
        if (dashboard instanceof ManagerDashboard) {

            ManagerDashboard managerDashboard = (ManagerDashboard) dashboard;

            // Display available clerks
            clerkSelector.getItems().setAll(managerDashboard.getClerks());

            // Display clerk name instead of object reference
            clerkSelector.setConverter(new StringConverter<Clerk>() {

                @Override
                public String toString(Clerk clerk) {
                    if (clerk == null) return "";
                    else return clerk.getFullName();
                }

                @Override
                public Clerk fromString(String string) {
                    return null;
                }
            });

            // Select first clerk automatically
            if (!clerkSelector.getItems().isEmpty()) {
                clerkSelector.getSelectionModel().selectFirst();
                displaySelectedClerk(managerDashboard);
            }

            // For clerk selection
            clerkSelector.setOnAction(event -> {
                displaySelectedClerk(managerDashboard);
            });

        } else {

            // Hide manager only section
            clerkSection.setVisible(false);
            clerkSection.setManaged(false);
        }
    }

    // Displays selected clerk statistics
    private void displaySelectedClerk(ManagerDashboard managerDashboard) {

        Clerk clerk = clerkSelector.getValue();

        if (clerk != null) {
            displayStats(clerkStatsContainer, managerDashboard.getClerkStats(clerk));
        }
    }

    // Displays statistics from a Map
    private void displayStats(VBox container, Map<String, String> stats) {
        container.getChildren().clear();

        for (Map.Entry<String, String> entry : stats.entrySet()) {	
            HBox statRow = createStatRow(entry.getKey(), entry.getValue());
            container.getChildren().add(statRow);
        }
    }
    
    private HBox createStatRow(String labelText, String valueText) {
        Label title = new Label(labelText);
        title.setStyle("-fx-text-fill: #7A6248; -fx-font-size: 12.5px; -fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label value = new Label(valueText);
        value.setStyle("-fx-text-fill: #3E2714; -fx-font-size: 14px; -fx-font-weight: bold;");

        HBox row = new HBox(10.0, title, spacer, value);
        row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        row.setStyle(
            "-fx-background-color: #FAF7EC;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #E7DEC4;" +
            "-fx-border-radius: 10;" +
            "-fx-padding: 10 14 10 14;"
        );
        
        return row;
    }
}

