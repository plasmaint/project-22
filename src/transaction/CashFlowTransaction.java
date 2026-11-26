package transaction;

import management.Inventory;

public class CashFlowTransaction extends Transaction {

	// Attributes
    private String flowType;
    private double amount;
    private String note;

    // Constructor
    public CashFlowTransaction(String transactionId, String recordedBy, String flowType, double amount, String note) {
        super(transactionId, recordedBy);

        if (!flowType.equals("Cash In") && !flowType.equals("Cash Out")) {
            throw new IllegalArgumentException("Invalid cash flow type.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }

        this.flowType = flowType;
        this.amount = amount;
        this.note = note;
    }

    @Override
    public String getType() {
        return flowType;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }

    @Override
    public void undoTransaction(Inventory inventory, Wallet wallet) {
        if (flowType.equals("Cash In")) {
            wallet.cashOut(amount);
        } else {
            wallet.cashIn(amount);
        }
    }
}