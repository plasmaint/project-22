package controller;

import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

    private Dashboard dashboard;

    // Receives the appropriate dashboard
    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;

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
            Label label = new Label(entry.getKey() + ": " + entry.getValue());

            container.getChildren().add(label);
        }
    }
}

