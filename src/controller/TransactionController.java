package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TransactionController {

	@FXML private Label walletBalanceLabel;
	@FXML private TableView<?> transactionTable;
    @FXML private TableColumn<?, ?> colAmount;
    @FXML private TableColumn<?, ?> colDateTime;
    @FXML private TableColumn<?, ?> colRecordedBy;
    @FXML private TableColumn<?, ?> colStatus;
    @FXML private TableColumn<?, ?> colTransactionId;
    @FXML private TableColumn<?, ?> colType;

    @FXML private ComboBox<?> flowTypeComboBox;
    @FXML private TextField amountField;
    @FXML private TextField noteField;
    @FXML private Button recordCashButton;

    @FXML private Button voidTransactionButton;
    @FXML private Label statusMessageLabel;
    
    @FXML
    public void handleRecordCashFlow() {
    	// TODO: Implement
    }

    @FXML
    public void handleVoidTransaction() {
    	// TODO: Display confirmation prompt:
    		// "Are you sure you wish to void this transaction?"
    		// Transaction ID | Type | Amount | Recorded By | Date & Time | Status
    		// "Enter 'void transaction' to confirm" - "Confirm" button
    	// TODO: Remove transaction
    }

}
