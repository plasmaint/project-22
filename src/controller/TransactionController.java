package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import transaction.Transaction;

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
    @FXML private Button backButton;
    @FXML private Label statusMessageLabel;

    @FXML 
    public void handleBack() {
    	// Might not be needed since transaction screen will replace content area, not switch screen.
    }

    @FXML
    public void handleRecordCashFlow() {
    	// TODO: Implement
    }

    @FXML
    public void handleVoidTransaction() {
    	// TODO: Implement
    }

}
