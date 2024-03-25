package Model;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private final ArrayList<Product> products;

    public DataBase(){
        products = new ArrayList<Product>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void removeProduct(Product product) {
        products.remove(product);

    }
}
