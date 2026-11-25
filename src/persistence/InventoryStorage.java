package persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import management.Inventory;
import management.InventoryItem;
import product.EggProduct;

public class InventoryStorage {
    private static final String PATH = "data/inventory.csv";
    private static final String HEADER = "productId,stockQuantity";

    private final String filePath;

    public InventoryStorage() {
        this(PATH);
    }

    public InventoryStorage(String filePath) {
        this.filePath = filePath;
    }

    public void loadInto(Inventory inventory, List<EggProduct> products, int defaultStock) throws IOException {
        if (inventory == null)
            throw new IllegalArgumentException("Inventory cannot be null.");
            
        if (products == null)
            throw new IllegalArgumentException("Products list cannot be null.");
            
        if (defaultStock < 0)
            throw new IllegalArgumentException("Default stock cannot be negative.");

        Map<String, EggProduct> productMap = new HashMap<>();
        
        for (EggProduct product : products) {
            productMap.put(product.getProductId(), product);
        }

        List<String[]> rows = CsvUtil.readAll(filePath, HEADER);
        Map<String, Integer> loadedStocks = new HashMap<>();

        for (int i = 0; i < rows.size(); i++) {
            String[] cols = rows.get(i);
            
            if (cols.length < 2) // Incorrect string structure
                throw new IOException("Malformed inventory row at line " + (i + 2) + " in " + filePath);

            String productId = cols[0].trim();
            int stockQuantity;
            
            try { // Number error handling
                stockQuantity = Integer.parseInt(cols[1].trim());
            } catch (NumberFormatException e) {
                throw new IOException("Invalid stock quantity at line " + (i + 2) + " in " + filePath, e);
            }

            if (stockQuantity < 0) // Negative check
                throw new IOException("Negative stock at line " + (i + 2) + " in " + filePath);
            
            if (productMap.containsKey(productId))
                loadedStocks.put(productId, stockQuantity);
        }

        inventory.getItems().clear();
        boolean needsInitialSave = rows.isEmpty();

        for (EggProduct product : products) {
            int stock = loadedStocks.getOrDefault(product.getProductId(), defaultStock);
            if (!loadedStocks.containsKey(product.getProductId())) {
                needsInitialSave = true;
            }
            
            inventory.getItems().add(new InventoryItem(product, stock));
        }

        if (needsInitialSave)
            save(inventory);
    }

    public void save(Inventory inventory) throws IOException {
        if (inventory == null)
            throw new IllegalArgumentException("Inventory cannot be null.");
        
        List<String[]> rows = new ArrayList<>();
        
        for (InventoryItem item : inventory.getItems()) {
            rows.add(new String[] {
                item.getProduct().getProductId(),
                Integer.toString(item.getStockQuantity())
            });
        }
        
        CsvUtil.writeAll(filePath, HEADER, rows);
    }
}