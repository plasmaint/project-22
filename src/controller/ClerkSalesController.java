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
    @FXML private TableColumn<SaleItem, String> colCartType;
    @FXML private TableColumn<SaleItem, String> colCartName;
    @FXML private TableColumn<SaleItem, Integer> colCartQuantity;
    @FXML private TableColumn<SaleItem, Double> colCartPrice;
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

    // Manager can't process sales
    public void setProcessSalesAllowed(boolean allowed) {
    	processSalesSection.setVisible(allowed);
    	processSalesSection.setManaged(allowed);
    }
    // Clerk can only view own history
 	public void setSelectClerkAllowed(boolean allowed) {
    	clerkSelector.setVisible(allowed);
    	clerkSelector.setManaged(allowed);
 	}

    private void setUpManagerView() {
        clerkSelector.getItems().setAll(system.getClerks());
        
        // Select first clerk automatically
        if (!clerkSelector.getItems().isEmpty()) {
            clerkSelector.getSelectionModel().selectFirst();
            loadHistory(clerkSelector.getValue());
        }
        
        // For clerk selection
        clerkSelector.setOnAction(event -> {
        	loadHistory(clerkSelector.getValue());
        });
    }
    private void setUpClerkView() {
        loadHistory((Clerk) user);
        
        productSelector.setItems(system.getInventory().getItems());
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
        for (SaleItem si : cart.getItems()) {
            if (si.getProduct().getProductId().equals(selected.getProduct().getProductId())) {
                inCart = si.getQuantity();
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
        totalValueLabel.setText(String.format("Total: ₱%.2f", cart.getAmount()));
    }

    private void setUpHistoryColumns() {
        colSaleId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colSaleDateTime.setCellValueFactory(new PropertyValueFactory<>("dateTimeText"));
        colSaleTotal.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colSaleStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void setUpCartColumns() {
        colCartType.setCellValueFactory(new PropertyValueFactory<>("eggType"));
        colCartType.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colCartQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}