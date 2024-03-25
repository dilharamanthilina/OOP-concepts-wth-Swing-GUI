package View;
import javax.swing.*;
import Model.WestminsterShoppingManager;
import View.LoginSignupWindow;


import javax.swing.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose an interface:");
        System.out.println("1: Console Menu");
        System.out.println("2: GUI");
        System.out.print("Enter your choice (1 or 2): ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            new WestminsterShoppingManager().showMenu();
        } else if (choice == 2) {

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginSignupWindow().setVisible(true);
                }
            });
        } else {
            System.out.println("Invalid choice. Please select 1 or 2.");
        }
    }
}