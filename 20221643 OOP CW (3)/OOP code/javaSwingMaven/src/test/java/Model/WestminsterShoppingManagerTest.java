package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterShoppingManagerTest {
    private WestminsterShoppingManager shoppingManager;
    private Product product;

    @BeforeEach
    public void setUp() {
        shoppingManager = new WestminsterShoppingManager();
        product = new Electronics("E002", "Computer", 3, 500.0, "Dell", 6); // Assuming Product has a default constructor
    }


    @Test
    void addProduct() {
        shoppingManager.addProduct(product);
        assertEquals(1, shoppingManager.getProducts().size());
    }

    @Test
    void deleteProduct() {
        // Precondition: Add a product to the shoppingManager
        shoppingManager.addProduct(product);
        assertEquals(1, shoppingManager.getProducts().size());

        // Action: Delete the product
        shoppingManager.deleteProduct(product.getId());

        // Verification: Check the product is no longer present
        assertFalse(shoppingManager.getProducts().contains(product));
        assertEquals(0, shoppingManager.getProducts().size());
    }

    @Test
    void deleteNonExistentProduct() {
        // Precondition: Add a product to the shoppingManager
        shoppingManager.addProduct(product);
        assertEquals(1, shoppingManager.getProducts().size());

        // Action: Attempt to delete a non-existent product
        shoppingManager.deleteProduct("non-existent-id");


        // Verification: Check the product list remains unchanged
        assertEquals(1, shoppingManager.getProducts().size());
        assertTrue(shoppingManager.getProducts().contains(product));
    }

    @Test
    void deleteProductWithNullId() {
        // Precondition: Add a product to the shoppingManager
        shoppingManager.addProduct(product);
        assertEquals(1, shoppingManager.getProducts().size());

        // Action: Attempt to delete a product with a null ID
        shoppingManager.deleteProduct(null);

        // Verification: Check the product list remains unchanged
        assertEquals(1, shoppingManager.getProducts().size());
        assertTrue(shoppingManager.getProducts().contains(product));
    }

    @Test
    void saveProductsToCSV() throws IOException {
        // Setup: Create a temporary file
        File tempFile = File.createTempFile("temp", ".csv");

        // Given
        shoppingManager.addProduct(product);
        String expectedOutput = "Class, ID, Name, Quantity, Price, Brand, Warranty, Size, Color\n" +
                "Electronics,E002,Computer,3,500.0,Dell,6,,";

        // When
        shoppingManager.saveProductsToCSV(tempFile.getAbsolutePath());

        // Then
        List<String> actualOutputLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(tempFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                actualOutputLines.add(line);
            }
        }
        String actualOutput = String.join("\n", actualOutputLines);
        assertEquals(expectedOutput, actualOutput);

        // Cleanup: Delete the temporary file
        tempFile.delete();
    }


    @Test
    void loadProductsFromCSV() throws Exception {
        // Arrange
        Path testCsvPath = Files.createTempFile("test-products", ".csv");
        List<String> lines = List.of(
                "Type,Id,Name,Quantity,Price,Brand,Warranty,Size,Color",
                "Electronics,E001,TV,10,1500.0,Samsung,24,,",
                "Clothing,C001,Shirt,20,29.99,,,XL,Red"
        );
        Files.write(testCsvPath, lines);

        // Act
        shoppingManager.loadProductsFromCSV(testCsvPath.toString());

        // Assert
        List<Product> products = shoppingManager.getProducts();
        assertNotNull(products);
        assertEquals(2, products.size());

        // More detailed assertions can be made here to ensure all products have the correct attributes
        // For example:
        assertTrue(products.get(0) instanceof Electronics);
        assertEquals("E001", products.get(0).getId());
        assertEquals("Samsung", ((Electronics) products.get(0)).getBrand());
        // ... and so on for other attributes and product types

        // Clean up the test file
        Files.deleteIfExists(testCsvPath);
    }
}