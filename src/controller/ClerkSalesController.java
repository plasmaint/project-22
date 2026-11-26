package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.util.StringConverter;
import management.InventoryItem;
import management.System;
import transaction.*;
import user.Clerk;
import user.User;

public class ClerkSalesController {
    @FXML private Label titleLabel;
    @FXML private Label subtitleLabel;
    
    @FXML private ComboBox<Clerk> clerkSelector;
    @FXML private TableView<SaleTransaction> historyTable;
    @FXML private TableColumn<SaleTransaction, String> colSaleId;
    @FXML private TableColumn<SaleTransaction, String> colSaleDateTime;
    @FXML private TableColumn<SaleTransaction, String> colSaleTotal;
    @FXML private TableColumn<SaleTransaction, Double> colSaleStatus;
    
    @FXML private VBox processSalesSection;
    @FXML private TableView<SaleItem> cartTable;
    @FXML private TableColumn<SaleItem, String> colCartProduct;
    @FXML private TableColumn<SaleItem, Integer> colCartQuantity;
    @FXML private TableColumn<SaleItem, Double> colCartPrice;
    @FXML private TableColumn<SaleItem, Double> colCartTotal;
    @FXML private ComboBox<InventoryItem> productSelector;
    @FXML private TextField quantityField;
    @FXML private Label statusLabel;
    @FXML private Label totalValueLabel;

    private System system;
    private User user;
    private SaleTransaction cart;

    public void setSystem(System system) {
        this.system = system;
        this.user = system.getCurrentUser();

        setUpHistoryColumns();

        if (user.isManager()) {
            setUpManagerView();
        } else {
            setUpClerkView();
        }
    }

    // Helper. Combines eggType, productName, and stockQuantity for the product selector in Sales Processing.
    private String combineLabel(InventoryItem item) {
        if (item == null) return "";
        return item.getEggType() + " (" + item.getProductName() + ")"
        		+ " - " + item.getStockQuantity();
    }
    
    // Configures processSalesSection VBox visisbility.
    // Used to hide the 'Sales Processing' section from manager view.
    public void setProcessSalesAllowed(boolean allowed) {
    	processSalesSection.setVisible(allowed);
    	processSalesSection.setManaged(allowed);
    }
    // Configures clerkSelector ComboBox visisbility.
 	// Used to hide the 'Clerk' choice box from clerk view.
    public void setSelectClerkAllowed(boolean allowed) {
    	clerkSelector.setVisible(allowed);
    	clerkSelector.setManaged(allowed);
 	}
    private void setUpManagerView() {
    	// Assigns clerks as choices in the clerk selector.
        clerkSelector.getItems().setAll(system.getClerks());
        
        // Defaults 'Clerk Sales History' table to first clerk's sales history.
        if (!clerkSelector.getItems().isEmpty()) {
            clerkSelector.getSelectionModel().selectFirst();
            loadHistory(clerkSelector.getValue());
        }
        
        // Loads history whenever a clerk is selected in the clerk selector.
        clerkSelector.setOnAction(event -> {
        	loadHistory(clerkSelector.getValue());
        });
    }
    private void setUpClerkView() {
    	// Loads sales history of user clerk.
        loadHistory((Clerk) user);
        
        // Assigns product labels as choices in the product selector.
        productSelector.setConverter(new StringConverter<InventoryItem>() {
            @Override public String toString(InventoryItem item) { return combineLabel(item); }
            @Override public InventoryItem fromString(String s) { return null; }
        });
        productSelector.setItems(system.getInventory().getItems());
        
        // Creates a fresh cart ready for sales processing.
        setUpCartColumns();
        startNewCart();
    }

    private void loadHistory(Clerk clerk) {
        if (clerk == null) {
        	historyTable.getItems().clear();
        	return;
        }
        
        historyTable.setItems(FXCollections.observableArrayList(
            system.getTransactionHandler().getSalesByUser(clerk.getUsername())));
    }

    private void startNewCart() {
        cart = new SaleTransaction(system.getTransactionHandler().generateTransactionId(),
                                   user.getUsername());
        refreshCart();
    }

    @FXML
    private void handleAddToCart() {
        InventoryItem selected = productSelector.getValue();
        if (selected == null) {
        	statusLabel.setText("Select a product first.");
        	return;
        }

        int quantity =  Integer.parseInt(quantityField.getText());

        // Count whats in the cart
        int inCart = 0;
        for (SaleItem saleItem : cart.getItems()) {
            if (saleItem.getProduct().getProductId().equals(selected.getProduct().getProductId())) {
                inCart = saleItem.getQuantity();
            }
        }
        if (!selected.stockEnough(inCart + quantity)) {
        	statusLabel.setText("Only " + selected.getStockQuantity() + " in stock.");
            return;
        }

        cart.addItem(selected.getProduct(), quantity);
        refreshCart();
        statusLabel.setText("");
    }

    @FXML
    private void handleRemoveFromCart() {
        SaleItem selected = cartTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            cart.getItems().remove(selected);
            refreshCart();
        }
    }

    @FXML
    private void handleCheckoutCart() {
        try {
            system.getTransactionHandler().recordSale(cart); // Records, deducts stock, handles wallet
            statusLabel.setText("Sale " + cart.getTransactionId() + " recorded.");
            loadHistory((Clerk) user);   // Update clerk sales history
            startNewCart();              // New cart, new ID
        } catch (IllegalArgumentException e) {
        	statusLabel.setText(e.getMessage()); // empty cart, not enough stock, etc.
        }
    }

    private void refreshCart() {
        cartTable.setItems(FXCollections.observableArrayList(cart.getItems()));
        cartTable.refresh(); // Re-read SaleItem values when adding to cart more than once
        totalValueLabel.setText(String.format("Total: ₱%.2f", cart.getAmount()));
    }

    private void setUpHistoryColumns() {
        colSaleId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colSaleDateTime.setCellValueFactory(new PropertyValueFactory<>("dateTimeText"));
        colSaleTotal.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colSaleStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void setUpCartColumns() {
        colCartProduct.setCellValueFactory(new PropertyValueFactory<>("productLabel"));
        colCartQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCartPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colCartTotal.setCellValueFactory(new PropertyValueFactory<>("priceTotal"));
    }
}