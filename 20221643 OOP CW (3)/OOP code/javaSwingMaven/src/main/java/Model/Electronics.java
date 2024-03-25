package Model;

public class Electronics extends Product {
    private String brand;
    private int warranty;

    public Electronics(String id, String name, int quantity, double price, String brand, int warranty) {
        super(id, name, quantity, price);
        this.brand = brand;
        this.warranty = warranty;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }
    @Override
    public String toString() {
        return "Electronics: " + super.toString() + ", Brand: " + brand + ", Warranty: " + warranty + " months";
    }
}




