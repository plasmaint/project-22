module CMSC22_PROJECT_GGEZ {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	
	opens main to javafx.graphics, javafx.fxml;
}
