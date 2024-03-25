package Model;

public class Clothing extends Product {
    private String size;
    private String color;

    public Clothing(String id, String name, int quantity, double price, String size, String color) {
        super(id, name, quantity, price);
        this.size = size;
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    @Override
    public String toString() {
        return "Clothing: " + super.toString() + ", Size: " + size + ", Color: " + color;
    }
}
