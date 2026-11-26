package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;

import management.System;
import transaction.Transaction;
import user.User;

public class TransactionController {

	@FXML private Label walletBalanceLabel;
	@FXML private TableView<Transaction> transactionTable;
	
    @FXML private TableColumn<Transaction, Double> colAmount;
    @FXML private TableColumn<Transaction, String> colDateTime;
    @FXML private TableColumn<Transaction, String> colRecordedBy;
    @FXML private TableColumn<Transaction, String> colStatus;
    @FXML private TableColumn<Transaction, String> colTransactionId;
    @FXML private TableColumn<Transaction, String> colType;

    @FXML private ComboBox<String> flowTypeComboBox;
    @FXML private TextField amountField;
    @FXML private TextField noteField;
    @FXML private Button recordCashButton;

    @FXML private Button voidTransactionButton;
    @FXML private Label statusMessageLabel;
    
    private System system;
    
    @FXML
    public void initialize() {
        colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colRecordedBy.setCellValueFactory(new PropertyValueFactory<>("recordedBy"));
        colDateTime.setCellValueFactory(new PropertyValueFactory<>("dateTimeText"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        flowTypeComboBox.setItems(FXCollections.observableArrayList("Cash In", "Cash Out"));
    }
    
    // Receives System from MainController
    public void setSystem(System system) {
        this.system = system;

        User user = system.getCurrentUser();

        // Only managers can record cash flow and void transactions
        boolean manager = user.isManager();

        flowTypeComboBox.setDisable(!manager);
        amountField.setDisable(!manager);
        noteField.setDisable(!manager);
        recordCashButton.setDisable(!manager);
        voidTransactionButton.setDisable(!manager);

        refreshTransactions();
    }
    
    // Refresh transaction table and wallet balance
    private void refreshTransactions() {
        ArrayList<Transaction> records;

        if (system.getCurrentUser().isManager()) {
            records = system.getTransactionHandler().getTransactions();
        } else {
            records = system.getTransactionHandler().getTransactionsByUser(system.getCurrentUser().getUsername());
        }
        transactionTable.getItems().setAll(records);

        walletBalanceLabel.setText("Wallet Balance: " + 
        String.format("₱%,.2f",system.getTransactionHandler().getWallet().getBalance()));
    }
    
    // Record Cash In / Cash Outs
    @FXML
    public void handleRecordCashFlow() {

        try {
            String flowType = flowTypeComboBox.getValue();
            String amountText = amountField.getText().trim();
            String note = noteField.getText().trim();

            if (flowType == null || amountText.isEmpty()) {
                throw new IllegalArgumentException("Please select a cash flow type and enter an amount.");
            }

            double amount = Double.parseDouble(amountText);

            String transactionId = system.getTransactionHandler().generateTransactionId();

            system.getTransactionHandler().recordCashFlow(
                transactionId,
                system.getCurrentUser().getUsername(),
                flowType,
                amount,
                note
            );
            
            statusMessageLabel.setText("Cash flow recorded successfully.");

            amountField.clear();
            noteField.clear();
            flowTypeComboBox.getSelectionModel().clearSelection();

            refreshTransactions();

        } catch (NumberFormatException e) {

            statusMessageLabel.setText("Please enter a valid amount.");

        } catch (IllegalArgumentException e) {

            statusMessageLabel.setText(e.getMessage());
        }
    }

    // Void selected transaction
    @FXML
    public void handleVoidTransaction() {
        Transaction selected = transactionTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            statusMessageLabel.setText("Please select a transaction first.");
            return;
        }

        if (selected.isVoided()) {
            statusMessageLabel.setText("This transaction is already voided.");
            return;
        }

        // First confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Void Transaction");
        alert.setHeaderText("Are you sure you wish to void this transaction?");

        alert.setContentText(
            "Transaction ID: " + selected.getTransactionId() +
            "\nType: " + selected.getType() +
            "\nAmount: " + String.format("₱%,.2f", selected.getAmount()) +
            "\nRecorded By: " + selected.getRecordedBy() +
            "\nDate & Time: " + selected.getDateTimeText() +
            "\nStatus: " + selected.getStatus()
        );

        if (alert.showAndWait().orElse(ButtonType.CANCEL)
                != ButtonType.OK) {
            return;
        }

        // Second confirmation
        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Confirm Void");
        dialog.setHeaderText("Confirm Transaction Voiding");
        dialog.setContentText("Enter 'void transaction' to confirm:");

        String confirmation = dialog.showAndWait().orElse("");

        if (!confirmation.trim().equalsIgnoreCase("void transaction")) {
            statusMessageLabel.setText("Voiding cancelled.");
            return;
        }

        try {
            system.getTransactionHandler().voidTransaction(selected.getTransactionId());
            statusMessageLabel.setText("Transaction voided successfully.");
            refreshTransactions();

        } catch (IllegalArgumentException e) {
            statusMessageLabel.setText(e.getMessage());
        }
    }

}
