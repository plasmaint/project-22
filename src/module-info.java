module CMSC22_PROJECT_GGEZ {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.base;

    opens controller to javafx.fxml;
    opens management to javafx.base, javafx.fxml;
    
    exports main;
    exports controller;
    exports management;
}