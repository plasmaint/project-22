package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import management.Inventory;
import product.EggProduct;
import product.ProductList;

public class ProductController {

	@FXML private Label productsUniqueAmount;
	@FXML private TableView<EggProduct> productsTable;
	@FXML private TableColumn<EggProduct, String> colProductId;
	@FXML private TableColumn<EggProduct, String> colType;
	@FXML private TableColumn<EggProduct, String> colName;
    @FXML private TableColumn<EggProduct, String> colDescription;
    @FXML private TableColumn<EggProduct, Double> colPrice;
    @FXML private TableColumn<EggProduct, Integer> colPackSize;
    @FXML private VBox productManagementBox;
    @FXML private Label statusMessageLabel;

	// Shared products list
	private ProductList productList;
    
	// Runs when screen loads
    @FXML
    public void initialize() {
        // Link columns to EggProduct property names
    	colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
    	colType.setCellValueFactory(new PropertyValueFactory<>("eggType"));
		colName.setCellValueFactory(new PropertyValueFactory<>("productName"));
		colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
    }
    
    public void setProducts(ProductList productList) {
    	this.productList = productList;
    	productsTable.setItems(productList.getProducts());
    }
    
    @FXML
    void handleAddNewProduct() {

    }

    @FXML
    void handleEditExistingProduct() {

    }

}
