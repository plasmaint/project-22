package persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import transaction.Wallet;

public class WalletStorage {
    private static final String PATH = "data/wallet.csv";
    private static final String HEADER = "balance";

    private final String filePath;

    public WalletStorage() {
        this(PATH);
    }

    public WalletStorage(String filePath) {
        this.filePath = filePath;
    }

    public Wallet loadOrDefault(double defaultStartingBalance) throws IOException {
        List<String[]> rows = CsvUtil.readAll(filePath, HEADER);
        
        if (rows.isEmpty()) {
            Wallet wallet = new Wallet(defaultStartingBalance);
            save(wallet);
            return wallet;
        }

        String[] firstRow = rows.get(0);
        
        if (firstRow.length == 0 || firstRow[0].trim().isEmpty()) {
            return new Wallet(defaultStartingBalance);
        }

        try {
            double balance = Double.parseDouble(firstRow[0].trim());
            return new Wallet(balance);
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid wallet balance in " + filePath + ": " + firstRow[0], e);
        }
    }

    public void save(Wallet wallet) throws IOException {
        if (wallet == null) {
            throw new IllegalArgumentException("Wallet cannot be null.");
        }
        
        List<String[]> rows = new ArrayList<>();
        
        // Formatting and writing
        rows.add(new String[] { String.format(java.util.Locale.US, "%.2f", wallet.getBalance()) });
        CsvUtil.writeAll(filePath, HEADER, rows);
    }
}