package persistence;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import management.Inventory;
import product.EggProduct;
import product.ProductList;
import transaction.TransactionHandler;
import transaction.Wallet;

public class PersistenceManager {
    private static final int DEFAULT_INITIAL_STOCK = 20;
    private static final double DEFAULT_STARTING_WALLET_BALANCE = 0.0;

    private final ProductStorage productStorage;
    private final InventoryStorage inventoryStorage;
    private final TransactionStorage transactionStorage;
    private final WalletStorage walletStorage;

    public PersistenceManager() {
        this(new ProductStorage(), new InventoryStorage(), new TransactionStorage(), new WalletStorage());
    }

    public PersistenceManager(ProductStorage productStorage, InventoryStorage inventoryStorage, TransactionStorage transactionStorage, WalletStorage walletStorage) {
        this.productStorage = productStorage;
        this.inventoryStorage = inventoryStorage;
        this.transactionStorage = transactionStorage;
        this.walletStorage = walletStorage;
    }

    public void loadAll(ProductList productList, Inventory inventory, TransactionHandler transactionHandler) throws IOException {
        if (productList == null)
            throw new IllegalArgumentException("ProductList cannot be null.");

        if (inventory == null)
            throw new IllegalArgumentException("Inventory cannot be null.");
        
        if (transactionHandler == null)
            throw new IllegalArgumentException("TransactionHandler cannot be null.");

        // Load products and sync ProductList
        List<EggProduct> products = productStorage.loadOrDefault();
        productList.getProducts().clear();
        productList.getProducts().addAll(products);

        // Load inventory stock matched by productId
        inventoryStorage.loadInto(inventory, products, DEFAULT_INITIAL_STOCK);

        // Load wallet balance and replace TransactionHandler's wallet reference
        Wallet loadedWallet = walletStorage.loadOrDefault(DEFAULT_STARTING_WALLET_BALANCE);
        setHandlerWallet(transactionHandler, loadedWallet);

        // Load transaction history and sale line items
        transactionStorage.loadInto(transactionHandler, products);
    }

    public void saveAll(ProductList productList, Inventory inventory, TransactionHandler transactionHandler) throws IOException {
        if (productList == null)
            throw new IllegalArgumentException("ProductList cannot be null.");

        if (inventory == null)
            throw new IllegalArgumentException("Inventory cannot be null.");
            
        if (transactionHandler == null)
            throw new IllegalArgumentException("TransactionHandler cannot be null.");

        productStorage.saveAll(productList.getProducts());
        inventoryStorage.save(inventory);
        walletStorage.save(transactionHandler.getWallet());
        transactionStorage.save(transactionHandler);
    }

    public ProductStorage getProductStorage() {
        return productStorage;
    }

    public InventoryStorage getInventoryStorage() {
        return inventoryStorage;
    }

    public TransactionStorage getTransactionStorage() {
        return transactionStorage;
    }

    public WalletStorage getWalletStorage() {
        return walletStorage;
    }

    private void setHandlerWallet(TransactionHandler handler, Wallet wallet) throws IOException {
        try {
            Field walletField = TransactionHandler.class.getDeclaredField("wallet");
            walletField.setAccessible(true);
            walletField.set(handler, wallet);
        } catch (ReflectiveOperationException e) {
            throw new IOException("Unable to attach persisted Wallet to TransactionHandler.", e);
        }
    }
}