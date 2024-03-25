package View;

import Model.Product;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ShoppingCartTableModel extends AbstractTableModel {
    private List<Product> products;
    private final String[] columnNames = {"Product", "Quantity", "Price"};

    public ShoppingCartTableModel(List<Product> products) {
        this.products = products;
    }

    @Override
    public int getRowCount() {
        return products.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = products.get(rowIndex);
        switch (columnIndex) {
            case 0: return product.getName();
            case 1: return product.getQuantity();
            case 2: return product.getPrice();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void clearTable() {
        products.clear();

    }

    public void setProducts(List<Product> products) {
        this.products = products;
        fireTableDataChanged();
    }
}
