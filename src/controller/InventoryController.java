package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import management.EggProduct;

public class InventoryController {

    @FXML private TableView<EggProduct> inventoryTable;
    @FXML private TableColumn<EggProduct, String> colProductName;
    @FXML private TableColumn<EggProduct, Integer> colStock;
    @FXML private TableColumn<EggProduct, Double> colPrice;
    @FXML private TableColumn<EggProduct, String> colBatchId;

    private final ObservableList<EggProduct> eggInventoryData = FXCollections.observableArrayList(
		new EggProduct("Chicken (Large)", 45, 320.00, "2026-001"),
        new EggProduct("Duck (Balut)", 30, 210.00, "2026-002"),
        new EggProduct("Goose (Fresh)", 11, 440.00, "2026-003")
    );

    @FXML
    public void initialize() {
        // Link columns to EggProduct property names
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batchId"));

        // Load data into the table
        inventoryTable.setItems(eggInventoryData);
    }
}