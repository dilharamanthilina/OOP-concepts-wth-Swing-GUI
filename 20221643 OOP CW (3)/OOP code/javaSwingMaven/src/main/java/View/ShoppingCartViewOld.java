package View;

import Model.ShoppingCart;

import javax.swing.*;
import java.awt.*;

public class ShoppingCartViewOld extends JFrame {
    private JTable table;
    ShoppingCartTableModel tableModel;

    private JLabel totalLabel;

    private JLabel firstPurchaseDiscountLabel;

    private JLabel threeItemsDiscountLabel;
    private JLabel finalTotalLabel;


    public ShoppingCartViewOld(ShoppingCart cart) {


        super("Swing View.App");

        setSize(600, 500);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        tableModel = new ShoppingCartTableModel(cart.getProducts());
        table = new JTable(tableModel);

        gc.gridx = 0;
        gc.gridy = 0; // Place the table below the combo box, hence 'gridy' is 2
        gc.gridwidth = GridBagConstraints.REMAINDER; // Span across all columns
        gc.weightx = 1.0; // Give X axis some weight to fill the space horizontally
        gc.weighty = 1.0; // IMPORTANT: Give Y axis some weight to fill the space vertically
        gc.fill = GridBagConstraints.BOTH; // Make the component expand in both directions
        gc.anchor = GridBagConstraints.CENTER; // Center the component
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, gc);


















        JLabel totalLabel = new JLabel("Total");
        totalLabel.setBorder(BorderFactory.createLineBorder(Color.RED)); // Debug border
        gc.gridx = 0;
        gc.gridy = 1;
        gc.insets = new Insets(20, 20, 0, 20);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(totalLabel, gc);

        JLabel totalValue = new JLabel("0.00");
        gc.gridx = 1;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LAST_LINE_END;
        add(totalValue, gc);

        JLabel firstPurchaseDiscountLabel = new JLabel("First Purchase Discount(10%)");
        gc.gridx = 0;
        gc.gridy = 2;
        add(firstPurchaseDiscountLabel, gc);

        JLabel firstPurchaseDiscountValue = new JLabel("- 0.00");
        gc.gridx = 1;
        gc.gridy = 2;
        add(firstPurchaseDiscountValue, gc);

        JLabel threeItemsDiscountLabel = new JLabel("Three Items in same Category Discount (20%)");
        gc.gridx = 0;
        gc.gridy = 3;
        add(threeItemsDiscountLabel, gc);

        JLabel threeItemsDiscountValue = new JLabel("- 0.00");
        gc.gridx = 1;
        gc.gridy = 3;
        add(threeItemsDiscountValue, gc);

        JLabel finalTotalLabel = new JLabel("Final Total");
        gc.gridx = 0;
        gc.gridy = 4;
        add(finalTotalLabel, gc);

        JLabel finalTotalValue = new JLabel(" 0.00");
        gc.gridx = 1;
        gc.gridy = 4;
        add(finalTotalValue, gc);

        setVisible(true);
    }


}
