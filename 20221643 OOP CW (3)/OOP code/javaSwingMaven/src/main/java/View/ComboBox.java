package View;

import javax.swing.*;
import java.awt.*;

public class ComboBox extends JPanel {
        private Button shoppingCartButton;
        public ComboBox(){
            shoppingCartButton = new Button("Shopping Cart");
            setLayout(new FlowLayout(FlowLayout.RIGHT));
            add(shoppingCartButton);
        }

    }