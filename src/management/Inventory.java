package management;


public class Inventory {
	public void addProduct(EggProduct product);

	public void removeProduct(EggProduct product);

	public void restockProduct(EggProduct product, int quantity);

	public void updateStock(EggProduct product, int quantity);

	public void updateProduct(EggProduct product, String name,
	                          String description, double price);

	public ObservableList<EggProduct> getProducts();

}
