package View;

import javax.swing.*;
import java.awt.*;

public class ToolBar extends JPanel {

    private Button shoppingCartButton;
    public ToolBar(){
        shoppingCartButton = new Button("Shopping Cart");
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(shoppingCartButton);
    }

}
