package View;

import Model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class MainFrame extends JFrame {
    private Button shoppingCartButton;
    private JComboBox comboBox;
    private JTable table;
    private Product selectedProduct;
    private ProductDetails productDetails;
    private List<Product> products;
    private ShoppingCart shoppingCart;
    ShoppingCartView shoppingCartView;
    private User currentUser;
    ProductTableModel tableModel;


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

    public void loadProductsFromCSV(String filePath) {
        products = new ArrayList<>();
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
    public MainFrame(User user){
        super("Swing View.App");
        this.currentUser = user;


        setSize(600, 500);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();


        shoppingCartButton = new Button("Shopping Cart");
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(5, 20, 0, 20);
        add(shoppingCartButton, gc);

        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoppingCartView = new ShoppingCartView(shoppingCart, currentUser, products, MainFrame.this);
            }
        });


        DefaultComboBoxModel<String> combomodel = new DefaultComboBoxModel<String>();
        comboBox = new JComboBox<>();
        gc.gridx = 0;
        gc.gridy = 1;
        gc.weightx = 1.0;
        gc.weighty = 0;
        gc.anchor = GridBagConstraints.CENTER;

        combomodel.addElement("All");
        combomodel.addElement("Electronics");
        combomodel.addElement("Clothing");
        comboBox.setModel(combomodel);

        add(comboBox, gc);

        loadProductsFromCSV("products.csv");

        tableModel = new ProductTableModel();
        tableModel.setData(products);
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        TableRowSorter<ProductTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        table.setDefaultRenderer(Object.class, new LowQuantityCellRenderer());


        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedCategory = (String) comboBox.getSelectedItem();
                    if ("All".equals(selectedCategory)) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter(selectedCategory, 2)); // Assuming the category is in the 3rd column, index 2
                    }
                }
            }
        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int viewIndex = table.getSelectedRow();
                    if (viewIndex >= 0) {
                        int modelIndex = table.convertRowIndexToModel(viewIndex);
                        selectedProduct = tableModel.getProductAt(modelIndex);
                        productDetails.updateProduct(selectedProduct);
                        productDetails.setVisible(true);

                    } else {
                        productDetails.setVisible(false);
                    }
                }
            }
        });



        gc.gridx = 0;
        gc.gridy = 2;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.weightx = 1.0;
        gc.weighty = 1.0;
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.CENTER;
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, gc);


       productDetails = new ProductDetails(selectedProduct);

        gc.gridx = 0;
        gc.gridy = 3;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.PAGE_START;

        add(productDetails, gc);
        productDetails.setVisible(false);

        shoppingCart = new ShoppingCart(currentUser);
        Button addToShoppingCartButton = new Button("Add to Shopping Cart");
        gc.gridx = 0;
        gc.gridy = 4;
        gc.weightx = 1;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(5, 20, 0, 20);
        add(addToShoppingCartButton, gc);

        addToShoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedProduct != null) {
                    shoppingCart.addProduct(selectedProduct);

                    shoppingCartView.updateLabels(shoppingCart);

                }
            }
        });

    }

}
