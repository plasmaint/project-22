package persistence;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import product.EggProduct;
import transaction.SaleItem;
import transaction.SaleTransaction;
import transaction.Transaction;
import transaction.TransactionHandler;

public class TransactionStorage {
    private static final String TRANSACTIONS_PATH = "data/transactions.csv";
    private static final String ITEMS_PATH = "data/sale_items.csv";
    private static final String TRANSACTIONS_HEADER = "transactionId,type,recordedBy,dateTime,voided,amount";
    private static final String ITEMS_HEADER = "transactionId,productId,quantity,unitPrice";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final String transactionsFilePath;
    private final String saleItemsFilePath;

    public TransactionStorage() {
        this(TRANSACTIONS_PATH, ITEMS_PATH);
    }

    public TransactionStorage(String transactionsFilePath, String saleItemsFilePath) {
        this.transactionsFilePath = transactionsFilePath;
        this.saleItemsFilePath = saleItemsFilePath;
    }

    public void loadInto(TransactionHandler handler, List<EggProduct> products) throws IOException {
        if (handler == null)
            throw new IllegalArgumentException("TransactionHandler cannot be null.");
            
        if (products == null)
            throw new IllegalArgumentException("Products list cannot be null.");

        Map<String, EggProduct> productMap = new HashMap<>();
        
        for (EggProduct product : products) {
            productMap.put(product.getProductId(), product);
        }

        List<String[]> itemRows = CsvUtil.readAll(saleItemsFilePath, ITEMS_HEADER);
        Map<String, List<SaleItem>> itemsByTransactionId = new LinkedHashMap<>();

        for (int i = 0; i < itemRows.size(); i++) {
            String[] cols = itemRows.get(i);
            
            if (cols.length < 4) {
                throw new IOException("Malformed sale item row at line " + (i + 2) + " in " + saleItemsFilePath);
            }

            String transactionId = cols[0].trim();
            String productId = cols[1].trim();
            EggProduct product = productMap.get(productId);
            
            if (product == null) {
                throw new IOException("Unknown productId '" + productId + "' at line " + (i + 2) + " in " + saleItemsFilePath);
            }

            int quantity;
            double unitPrice;
            
            try {
                quantity = Integer.parseInt(cols[2].trim());
                unitPrice = Double.parseDouble(cols[3].trim());
            } catch (NumberFormatException e) {
                throw new IOException("Invalid number format at line " + (i + 2) + " in " + saleItemsFilePath, e);
            }

            SaleItem saleItem = new SaleItem(product, quantity);
            setSaleItemUnitPrice(saleItem, unitPrice);
            itemsByTransactionId.computeIfAbsent(transactionId, k -> new ArrayList<>()).add(saleItem);
        }

        List<String[]> transactionRows = CsvUtil.readAll(transactionsFilePath, TRANSACTIONS_HEADER);
        List<Transaction> loadedTransactions = new ArrayList<>();

        for (int i = 0; i < transactionRows.size(); i++) {
            String[] cols = transactionRows.get(i);
            
            if (cols.length < 6) {
                throw new IOException("Malformed transaction row at line " + (i + 2) + " in " + transactionsFilePath);
            }

            String transactionId = cols[0].trim();
            String type = cols[1].trim();
            String recordedBy = cols[2].trim();

            LocalDateTime dateTime;
            boolean voided;
            
            try {
                dateTime = LocalDateTime.parse(cols[3].trim(), FORMATTER);
                voided = Boolean.parseBoolean(cols[4].trim());
            } catch (Exception e) {
                throw new IOException("Invalid transaction metadata at line " + (i + 2) + " in " + transactionsFilePath, e);
            }

            if ("Sale".equalsIgnoreCase(type)) {
                SaleTransaction sale = new SaleTransaction(transactionId, recordedBy);
                setTransactionDateTime(sale, dateTime);

                List<SaleItem> items = itemsByTransactionId.get(transactionId);
                
                if (items != null)
                    sale.getItems().addAll(items);
                
                if (voided)
                    sale.markVoided();
                
                loadedTransactions.add(sale);
            } else {
                throw new IOException("Unsupported transaction type '" + type + "' at line " + (i + 2));
            }
        }

        handler.getTransactions().clear();
        handler.getTransactions().addAll(loadedTransactions);
    }

    public void save(TransactionHandler handler) throws IOException {
        if (handler == null)
            throw new IllegalArgumentException("TransactionHandler cannot be null.");

        List<String[]> transactionRows = new ArrayList<>();
        List<String[]> itemRows = new ArrayList<>();

        for (Transaction t : handler.getTransactions()) {
        	transactionRows.add(new String[] {
                t.getTransactionId(),
                t.getType(),
                t.getRecordedBy(),
                t.getDateTime().format(FORMATTER),
                Boolean.toString(t.isVoided()),
                String.format(Locale.US, "%.2f", t.getAmount())
            });

            if (t instanceof SaleTransaction sale) {
                for (SaleItem item : sale.getItems()) {
                    itemRows.add(new String[] {
                        sale.getTransactionId(),
                        item.getProduct().getProductId(),
                        Integer.toString(item.getQuantity()),
                        String.format(Locale.US, "%.2f", item.getUnitPrice())
                    });
                }
            }
        }

        CsvUtil.writeAll(transactionsFilePath, TRANSACTIONS_HEADER, transactionRows);
        CsvUtil.writeAll(saleItemsFilePath, ITEMS_HEADER, itemRows);
    }

    private void setTransactionDateTime(Transaction transaction, LocalDateTime dateTime) throws IOException {
        try {
            Field field = Transaction.class.getDeclaredField("dateTime");
            field.setAccessible(true);
            field.set(transaction, dateTime);
        } catch (ReflectiveOperationException e) {
            throw new IOException("Unable to restore transaction timestamp.", e);
        }
    }

    private void setSaleItemUnitPrice(SaleItem item, double unitPrice) throws IOException {
        try {
            Field field = SaleItem.class.getDeclaredField("unitPrice");
            field.setAccessible(true);
            field.set(item, unitPrice);
        } catch (ReflectiveOperationException e) {
            throw new IOException("Unable to restore sale item unit price.", e);
        }
    }
}