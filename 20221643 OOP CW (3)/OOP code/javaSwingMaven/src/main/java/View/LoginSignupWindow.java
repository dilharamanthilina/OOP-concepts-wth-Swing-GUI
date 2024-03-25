package View;

import Model.Clothing;
import Model.Electronics;
import Model.Product;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginSignupWindow extends JFrame implements ActionListener {
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;

    public LoginSignupWindow() {
        createWindow();
        addComponents();
        addActionEvent();
    }

    public void createWindow() {
        setTitle("Login and Signup Form");
        setSize(350, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void addComponents() {
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 10, 80, 25);
        add(userLabel);

        userTextField = new JTextField();
        userTextField.setBounds(140, 10, 150, 25);
        add(userTextField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 40, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(140, 40, 150, 25);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(50, 80, 100, 25);
        add(loginButton);

        signupButton = new JButton("Signup");
        signupButton.setBounds(190, 80, 100, 25);
        add(signupButton);
    }

    public void addActionEvent() {
        loginButton.addActionListener(this);
        signupButton.addActionListener(this);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {

            String username = userTextField.getText();
            String password = new String(passwordField.getPassword());
            User user = authenticate(username, password);
            if (user != null) {

                this.setVisible(false);
                new MainFrame(user).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        } else if (e.getSource() == signupButton) {

            String username = userTextField.getText();
            String password = new String(passwordField.getPassword());
            User user = new User(username, password);
            if (user == null) {
                System.out.println("user is nulrin");

            }
            registerNewUser(username, password);

            this.setVisible(false);
            new MainFrame(user).setVisible(true);
        }
    }


    private User authenticate(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("credentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(":");
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    List<Product> products = new ArrayList<>();

                    for (int i = 2; i < credentials.length; i++) {
                        products.add(convertStringToProduct(credentials[i]));
                    }
                    return new User(username, password, products);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Product convertStringToProduct(String credential) {
            Product product = null;

            if (credential != null) {
                String[] details = credential.split(",");
                String className = details[0];
                String id = details[1];
                String name = details[2];
                int quantity = Integer.parseInt(details[3]);
                double price = Double.parseDouble(details[4]);

                if ("Electronics".equals(className)) {
                    String brand = details[5];
                    int warranty = Integer.parseInt(details[6]);
                    product = new Electronics(id, name, quantity, price, brand, warranty);
                } else if ("Clothing".equals(className)) {
                    String size = details[7];
                    String color = details[8];
                    product = new Clothing(id, name, quantity, price, size, color);
                }
            }
            return product;
    }

    private void registerNewUser(String username, String password) {
        try (FileWriter writer = new FileWriter("credentials.txt", true)) {
            writer.write(username + ":" + password + "\n"); // Simple format: username:password
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginSignupWindow().setVisible(true);
            }
        });
    }
}
