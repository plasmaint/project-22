module CMSC22_PROJECT_GGEZ {
    requires javafx.controls;
    requires javafx.fxml;

    opens controller to javafx.fxml;
    opens management to javafx.base, javafx.fxml;
    
    exports main;
    exports controller;
    exports management;
}