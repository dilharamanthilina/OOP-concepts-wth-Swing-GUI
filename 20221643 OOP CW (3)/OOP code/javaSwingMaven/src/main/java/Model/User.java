package Model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;

    private List<Product> productHistory;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        productHistory = new ArrayList<>();

    }

    public User(String username, String password, List<Product> products) {
        this.username = username;
        this.password = password;
        this.productHistory = products;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Product> getProductHistory() {
        return productHistory;
    }

    public void appendProductHistory(Product product) {
        this.productHistory.add(product);
    }
}
