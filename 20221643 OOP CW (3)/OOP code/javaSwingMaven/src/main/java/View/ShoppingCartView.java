package View;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ShoppingCartView extends JFrame implements ShoppingCartListener{
    private JTable table;
    ShoppingCartTableModel tableModel;

    private ShoppingCart shoppingCart;

    private JLabel[] valueLabels;

    private List<Product> products;

    private MainFrame mainframe;

    public ShoppingCartView(ShoppingCart cart, User user, List<Product> products, MainFrame mainFrame) {
        super("Shopping Cart View");
        shoppingCart = cart;
        this.products = products;
        this.mainframe = mainFrame;

        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();


        tableModel = new ShoppingCartTableModel(cart.getProducts());
        table = new JTable(tableModel);


        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0;
        gc.weighty = 1.0;
        gc.insets = new Insets(20, 20, 20, 20); // Padding between components
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, gc);


        gc.gridwidth = 1;
        gc.weighty = 0; // Reset vertical weight for labels
        gc.anchor = GridBagConstraints.LINE_START;

        String[] labelNames = {
                "Total:", "First Purchase Discount (10%):",
                "Three Items Discount (20%):", "Final Total:"
        };

        valueLabels = new JLabel[4];

        for (int i = 0; i < labelNames.length; i++) {

            gc.gridx = 0;
            gc.gridy = i + 1;
            gc.fill = GridBagConstraints.HORIZONTAL;
            add(new JLabel(labelNames[i]), gc);


            valueLabels[i] = new JLabel("0.00", SwingConstants.RIGHT);
            valueLabels[i].setBorder(BorderFactory.createLineBorder(Color.RED));
            gc.gridx = 1;
            gc.gridy = i + 1;
            gc.anchor = GridBagConstraints.LINE_END;
            gc.fill = GridBagConstraints.NONE;
            add(valueLabels[i], gc);
        }

            JButton purchaseButton = new JButton("Purchase");
            gc.gridx = 1;
            gc.gridy = labelNames.length+1;
            add(purchaseButton, gc);
            updateLabels(cart);
            purchaseButton.addActionListener(e -> {
                appendProductsToCredentials(user, shoppingCart);
                updateProductListAndSaveToFile(shoppingCart.getProducts());

                tableModel.clearTable();


                for (JLabel label : valueLabels) {
                    label.setText("0.00 £");
                }


                shoppingCart.clearCart();
                tableModel.fireTableDataChanged();
                mainframe.tableModel.fireTableDataChanged();


            });
        setVisible(true);
    }
    public void updateLabels(ShoppingCart cart) {
        valueLabels[0].setText(String.format("%.2f", cart.calculateTotal()) + " £");
        valueLabels[1].setText(String.format("-%.2f", cart.firstPurChaseDiscount()) + " £");
        valueLabels[2].setText(String.format("-%.2f", cart.sameCategoryDiscount()) + " £");
        valueLabels[3].setText(String.format("%.2f", cart.calculateTotal() - (cart.sameCategoryDiscount()) - cart.firstPurChaseDiscount()) + " £");
        tableModel.fireTableDataChanged();
    }
    @Override
    public void cartUpdated(ShoppingCart cart) {
        updateLabels(cart);
        tableModel.setProducts(cart.getProducts());
        tableModel.fireTableDataChanged();
    }

    public void appendProductsToCredentials(User user, ShoppingCart shoppingCart) {
        Path credentialsPath = Paths.get("credentials.txt");
        Charset charset = StandardCharsets.UTF_8;
        String username = user.getUsername();
        boolean userFound = false;
        System.out.println(username);
        String shoppingCartContent = formatShoppingCart(shoppingCart);
        System.out.println("shoppingcart content: " + shoppingCartContent);

        try {
            List<String> lines = Files.readAllLines(credentialsPath, charset);
            System.out.println(lines);
            for (int i = 0; i < lines.size(); i++) {
                System.out.println(lines.get(i));
                if (lines.get(i).startsWith(username + ":")) {
                    lines.set(i, lines.get(i) + shoppingCartContent);
                    userFound = true;
                    break;
                }
            }

            if (!userFound) {
                System.out.println("no user found");
            } else {
                Files.write(credentialsPath, lines, charset);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatShoppingCart(ShoppingCart cart) {
        StringBuilder sb = new StringBuilder();
        for (Product product : cart.getProducts()) {
            sb.append(':');
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
        }
        return sb.toString();
    }


    public void updateProductListAndSaveToFile(List<Product> purchasedProducts) {
        System.out.println(purchasedProducts);

        for (Product purchasedProduct : purchasedProducts) {
            for (Product product : products) {
                if (product.getId().equals(purchasedProduct.getId())) {
                    int currentQuantity = product.getQuantity();
                    System.out.println(currentQuantity);
                    product.setQuantity(currentQuantity - 1);
                    break;
                }
            }
        }


        saveProductsToCSV("products.csv");
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

}
