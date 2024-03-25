package Model;

import View.ShoppingCartListener;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> products;
    private User currentUser;

    public ShoppingCart(User user) {
        products = new ArrayList<>();
        this.currentUser = user;
    }

    public void addProduct(Product product) {
        products.add(product);
        notifyListeners();
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public double calculateTotal() {
        double total = 0;
        for(Product p : products) {
            total += p.getPrice();
        }
        return total;
    }
    public List<Product> getProducts() {
        return products;
    }

    public double sameCategoryDiscount() {
        int electronicsCount = 0;
        int clothingCount = 0;
        for(Product p : products) {
            if (p instanceof Electronics) {
                ++electronicsCount;
            } else if (p instanceof Clothing) {
                ++clothingCount;
            }
        }

        if (electronicsCount >= 3 || clothingCount >= 3){
            return calculateTotal()*0.2;
        }
        return 0.0;

    }

    public double firstPurChaseDiscount(){
        if(currentUser.getProductHistory().size()>0){

            return 0.00;
        }
        return calculateTotal()*0.1;

    }

    public void clearCart() {
        products.clear();
    }

    private final List<ShoppingCartListener> listeners = new ArrayList<>();

    public void addListener(ShoppingCartListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ShoppingCartListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (ShoppingCartListener listener : listeners) {
            listener.cartUpdated(this);
        }
    }

}
