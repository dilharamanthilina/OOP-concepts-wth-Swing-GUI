package View;

import Model.Clothing;
import Model.Electronics;
import Model.Product;

import javax.swing.*;
import java.awt.*;

public class ProductDetails extends JPanel {
    JLabel product_id;
    JLabel value_product_id;
    JLabel name;
    JLabel value_name;
    JLabel items_available;
    JLabel value_items_available;
    JLabel size_or_brand;
    JLabel value_size_or_brand;
    JLabel color_or_warranty;
    JLabel value_color_or_warranty;

    public ProductDetails(Product product){
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        JLabel header = new JLabel("Selected Product - Details");
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
        gc.weightx = 0;
        gc.weighty = 0;

        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(20, 20, 0, 20);
        add(header, gc);

        product_id = new JLabel("Product ID:");
        gc.gridx = 0;
        gc.gridy = 1;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(20, 20, 0, 20);
        add(product_id, gc);

        value_product_id = new JLabel("Product ID Value");
        gc.gridx = 1;
        gc.gridy = 1;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(20, 20, 0, 20);
        add(value_product_id, gc);

        name = new JLabel("Name:");
        gc.gridx = 0;
        gc.gridy = 2;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(20, 20, 0, 20);
        add(name, gc);

        value_name = new JLabel("Name Value");
        gc.gridx = 1;
        gc.gridy = 2;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(20, 20, 0, 20);
        add(value_name, gc);

        size_or_brand = new JLabel("Size:");
        gc.gridx = 0;
        gc.gridy = 3;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(20, 20, 0, 20);
        add(size_or_brand, gc);

        value_size_or_brand = new JLabel("Size/Brand value");
        gc.gridx = 1;
        gc.gridy = 3;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(20, 20, 0, 20);
        add(value_size_or_brand, gc);

        color_or_warranty = new JLabel("Color:");
        gc.gridx = 0;
        gc.gridy = 4;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(20, 20, 0, 20);
        add(color_or_warranty, gc);

        value_color_or_warranty = new JLabel("Color/Warranty value");
        gc.gridx = 1;
        gc.gridy = 4;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(20, 20, 0, 20);
        add(value_color_or_warranty, gc);



        items_available = new JLabel("Items Available:");
        gc.gridx = 0;
        gc.gridy = 5;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(20, 20, 0, 20);
        add(items_available, gc);

        value_items_available = new JLabel("avaliable");
        gc.gridx = 1;
        gc.gridy = 5;
        gc.weightx = 1;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(20, 20, 0, 20);
        add(value_items_available , gc);
    }

    public void updateProduct(Product product) {
        value_product_id.setText(product.getId());
        value_name.setText(product.getName());
        if (product instanceof Clothing) {
            size_or_brand.setText("Size:");
            value_size_or_brand.setText(((Clothing) product).getSize());
            color_or_warranty.setText("Color:");
            value_color_or_warranty.setText(((Clothing) product).getColor());
        } else if (product instanceof Electronics) {
            size_or_brand.setText("Brand:");
            value_size_or_brand.setText(((Electronics) product).getBrand());
            color_or_warranty.setText("Warranty:");
            value_color_or_warranty.setText(String.valueOf(((Electronics) product).getWarranty()));
        }
        value_items_available.setText(String.valueOf(product.getQuantity()));
    }


}
