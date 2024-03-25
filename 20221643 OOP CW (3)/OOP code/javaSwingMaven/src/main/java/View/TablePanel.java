package View;

import javax.swing.*;
import java.awt.*;

public class TablePanel extends JPanel {
    private JTable table;
    private ProductTableModel tableModel;

    public TablePanel(){
        tableModel = new ProductTableModel();
        table = new JTable(tableModel);
        setLayout(new BorderLayout());
        add(table, BorderLayout.CENTER);
    }
}
