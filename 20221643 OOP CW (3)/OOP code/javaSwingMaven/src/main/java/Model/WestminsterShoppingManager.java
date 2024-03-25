package Model;

import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if(products.size() < 50) {
            products.add(product);
        } else {
            System.out.println("Cannot add product. Limit of 50 products reached.");
        }
    }

    public void deleteProduct(String id) {
        for(Product p : products) {
            if(p.getId().equals(id)) {
                products.remove(p);
                System.out.println("Deleted product");
                System.out.println(p);
                System.out.println("Remaining products: " + products.size());
                return;
            }
        }
        System.out.println("Product with id " + id + " not found");
    }

    public void printProducts() {
        Collections.sort(products, Comparator.comparing(Product::getId));
        for(Product product : products) {
            System.out.println(product);
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void saveProducts(){
        try {
            FileOutputStream fos = new FileOutputStream("products.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(products);
            oos.close();
            fos.close();
            System.out.println("Products saved to file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProducts(){
        try {
            FileInputStream fis = new FileInputStream("products.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            products = (List<Product>) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("Products loaded from file");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Product cilAddProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a product category:");
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");
        int category = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter product ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        if (category == 1) {
            System.out.print("Enter the Brand:");
            String brand = scanner.nextLine();
            System.out.print("Enter the warranty period in months:");
            int warranty = scanner.nextInt();
            return new Electronics(id, name, quantity, price, brand, warranty);
        } else if (category == 2) {
            System.out.print("Enter the size:");
            String size = scanner.nextLine();
            System.out.print("Enter the color");
            String color = scanner.nextLine();
            return new Clothing(id, name, quantity, price, size, color);
        }
        return null;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Choose from these choices");
            System.out.println("-------------------------\n");
            System.out.println("1 - Add a new product");
            System.out.println("2 - Delete a product");
            System.out.println("3 - List all products");
            System.out.println("4 - Save products to file");
            System.out.println("5 - Load products from file");
            System.out.println("6 - Quit");

            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    Product product = cilAddProduct();
                    if (product != null) {
                        addProduct(product);
                    }
                    break;
                case 2:
                    System.out.print("Enter product ID to delete: ");
                    scanner.nextLine();
                    String id = scanner.nextLine();
                    deleteProduct(id);
                    break;
                case 3:
                    printProducts();
                    break;
                case 4:
                    saveProductsToCSV("products.csv");
                    break;
                case 5:
                    loadProductsFromCSV("products.csv");
                    break;
                case 6:
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid number.");
                    break;
            }
        } while (choice != 6);
        scanner.close();
    }

    public void saveProductsToCSV(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("Class, ID, Name, Quantity, Price, Brand, Warranty, Size, Color");
            bw.newLine();

            for (Product product : products) {
                StringBuilder sb = new StringBuilder();
                sb.append(product.getClass().getSimpleName());
                sb.append(',');
                sb.append(product.getId());
                sb.append(',');
                sb.append(product.getName());
                sb.append(',');
                sb.append(product.getQuantity());
                sb.append(',');
                sb.append(product.getPrice());

                if (product instanceof Electronics) {
                    Electronics electronics = (Electronics) product;
                    sb.append(',');
                    sb.append(electronics.getBrand());
                    sb.append(',');
                    sb.append(electronics.getWarranty());

                    sb.append(",,");
                } else if (product instanceof Clothing) {
                    Clothing clothing = (Clothing) product;

                    sb.append(",,");
                    sb.append(',');
                    sb.append(clothing.getSize());
                    sb.append(',');
                    sb.append(clothing.getColor());
                } else {
                    sb.append(",,,,");
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProductsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                String className = details[0];
                String id = details[1];
                String name = details[2];
                int quantity = Integer.parseInt(details[3]);
                double price = Double.parseDouble(details[4]);

                if ("Electronics".equals(className)) {
                    String brand = details[5];
                    int warranty = Integer.parseInt(details[6]);
                    products.add(new Electronics(id, name, quantity, price, brand, warranty));
                } else if ("Clothing".equals(className)) {
                    String size = details[7];
                    String color = details[8];
                    products.add(new Clothing(id, name, quantity, price, size, color));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
