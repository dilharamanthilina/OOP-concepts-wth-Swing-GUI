package View;

import Model.Clothing;
import Model.Electronics;
import Model.Product;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProductTableModel extends AbstractTableModel {
    private List<Product> db;
    String[] columnNames = {"Product_Id", "Name", "Category", "Price", "Info"};

    public ProductTableModel() {
        this.db = new ArrayList<>();
    }

    public void setData(List<Product> db) {
        this.db = db;

        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return db.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = db.get(rowIndex);
        switch (columnIndex) {
            case 0: return product.getId();
            case 1: return product.getName();
            case 2: return product instanceof Electronics ? "Electronics" : "Clothing";
            case 3: return product.getPrice();
            case 4:
                if (product instanceof Electronics) {
                    String brandInfo =  ((Electronics) product).getBrand();
                    int warrantyInfo =  ((Electronics) product).getWarranty();
                    return brandInfo + warrantyInfo;
                } else if (product instanceof Clothing) {
                    String sizeInfo =  ((Clothing) product).getSize();
                    String colorInfo =  ((Clothing) product).getColor();
                    return sizeInfo + colorInfo;

                } else {
                    return null;
                }
        }
        return null;
    }


    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Product getProductAt(int index) {
        return db.get(index);
    }
}
