package persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import product.ChickenEgg;
import product.DuckEgg;
import product.EggProduct;
import product.GooseEgg;
import product.ProductList;
import product.QuailEgg;

public class ProductStorage {
    private static final String PATH = "data/products.csv";
    private static final String HEADER = "productId,eggType,productName,description,price,packSize";

    private final String filePath;

    public ProductStorage() {
        this(PATH);
    }

    public ProductStorage(String filePath) {
        this.filePath = filePath;
    }

    public List<EggProduct> loadOrDefault() throws IOException {
        List<String[]> rows = CsvUtil.readAll(filePath, HEADER);
        
        if (rows.isEmpty()) {
            List<EggProduct> defaults = new ProductList().getProducts();
            saveAll(defaults);
            return defaults;
        }

        List<EggProduct> loaded = new ArrayList<>();
        
        for (int i = 0; i < rows.size(); i++) {
            String[] cols = rows.get(i);
            
            if (cols.length < 6) {
                throw new IOException("Malformed product row at line " + (i + 2) + " in " + filePath);
            }

            String productId = cols[0].trim();
            String eggType = cols[1].trim();
            String productName = cols[2].trim();
            String description = cols[3].trim();

            double price;
            int packSize;
            
            try {
                price = Double.parseDouble(cols[4].trim());
                packSize = Integer.parseInt(cols[5].trim());
                loaded.add(createEggProduct(eggType, productId, productName, description, price, packSize));
            } catch (IllegalArgumentException e) {
                throw new IOException("Invalid product data at line " + (i + 2) + " in " + filePath, e);
            }
        }
        
        return loaded;
    }

    public void saveAll(List<EggProduct> products) throws IOException {
        if (products == null) {
            throw new IllegalArgumentException("Products list cannot be null.");
        }
        
        List<String[]> rows = new ArrayList<>();
        
        for (EggProduct p : products) {
            rows.add(new String[] {
                p.getProductId(),
                p.getEggType(),
                p.getProductName(),
                p.getDescription(),
                String.format(Locale.US, "%.2f", p.getPrice()),
                Integer.toString(p.getPackSize())
            });
        }
        
        CsvUtil.writeAll(filePath, HEADER, rows);
    }

    private EggProduct createEggProduct(
            String eggType,
            String productId,
            String productName,
            String description,
            double price,
            int packSize) {
        return switch (eggType.toLowerCase(Locale.ROOT)) {
            case "chicken" -> new ChickenEgg(productId, productName, description, price, packSize);
            case "duck" -> new DuckEgg(productId, productName, description, price, packSize);
            case "quail" -> new QuailEgg(productId, productName, description, price, packSize);
            case "goose" -> new GooseEgg(productId, productName, description, price, packSize);
            default -> throw new IllegalArgumentException("Unknown egg type: " + eggType);
        };
    }
}