package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.estore.api.estoreapi.model.Product;

/**
 * The unit test suite for the Product class
 * 
 * @author Brian Lin,Tyrone Tha, Alan Wang, Diego Avila
 */
@Tag("Model-tier")
public class ProductTest {
    @Test
    public void testCtor() {
        // Setup
        String expected_name = "kiwi";
        double expected_price = 4.99;
        int expected_quantity = 10;

        // Invoke
        Product product = new Product(expected_name, expected_price, expected_quantity);

        // Analyze
        assertEquals(expected_name, product.getName());
        assertEquals(expected_price, product.getPrice());
        assertEquals(expected_quantity, product.getQuantity());
    }

    @Test
    public void testName() {
        // Setup
        String name = "kiwi";
        double price = 4.99;
        int quantity = 10;
        Product product = new Product(name, price, quantity);

        String expected_name = "pear";

        // Invoke
        product.setName(expected_name);

        // Analyze
        assertEquals(expected_name, product.getName());
    }

    @Test
    public void testPrice() {
        // Setup
        String name = "kiwi";
        double price = 4.99;
        int quantity = 10;
        Product product = new Product(name, price, quantity);

        double expected_price = 5.99;

        // Invoke
        product.setPrice(expected_price);

        // Analyze
        assertEquals(expected_price, product.getPrice());
    }

    @Test
    public void testQuantity() {
        // Setup
        String name = "kiwi";
        double price = 4.99;
        int quantity = 10;
        Product product = new Product(name, price, quantity);

        int expected_quantity = 11;

        // Invoke
        product.setQuantity(expected_quantity);

        // Analyze
        assertEquals(expected_quantity, product.getQuantity());
    }

    @Test
    public void testToString() {
        // Setup
        String name = "kiwi";
        double price = 4.99;
        int quantity = 10;
        String expected_string = String.format(Product.STRING_FORMAT, name, price, quantity);
        Product product = new Product(name, price, quantity);

        // Invoke
        String actual_string = product.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }
    
}
