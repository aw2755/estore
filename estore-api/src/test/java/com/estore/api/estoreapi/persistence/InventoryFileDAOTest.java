package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Inventory File DAO class
 * 
 * @author Brian Lin, Tyrone Tha, Diego Avila
 */
@Tag("Persistence-tier")
public class InventoryFileDAOTest {
    InventoryFileDAO inventoryFileDAO;
    Product[] testProducts;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupProductFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testProducts = new Product[3];
        testProducts[0] = new Product("apple", 4.99, 10);
        testProducts[1] = new Product("kiwi", 5.99, 11);
        testProducts[2] = new Product("orange", 6.99, 12);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
        .readValue(new File("doesn't_matter.txt"), Product[].class))
        .thenReturn(testProducts);

        inventoryFileDAO = new InventoryFileDAO("doesn't_matter.txt", mockObjectMapper);
    }

    @Test
    public void testGetProducts() {
        // Invoke
        Product[] products = inventoryFileDAO.getProducts();

        // Analyze
        assertEquals(products.length, testProducts.length);
        for (int i = 0; i < testProducts.length; i++) {
            assertEquals(products[i], testProducts[i]);
        }
    }

    @Test
    public void testFindProducts() {
        // Invoke
        Product[] products = inventoryFileDAO.findProducts("ap");

        // Analyze
        assertEquals(products.length, 1);
        assertEquals(products[0], testProducts[0]);
    }

    @Test
    public void testGetProduct() {
        // Invoke
        Product product = inventoryFileDAO.getProduct("apple");

        // Analyze
        assertEquals(product, testProducts[0]);
    }

    @Test
    public void testDeleteHero() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> inventoryFileDAO.deleteProduct("apple"), 
        "Unexpected exception thrown");

        // Analyze
        assertEquals(result, true);
        // We check the internal tree map size against the length
        // of the test products array - 1 (because of the delete)
        // Because products attribute of InventoryFileDAO is package private
        // we can access it directly
        assertEquals(inventoryFileDAO.products.size(), testProducts.length-1);
    }

    @Test
    public void testCreateHero() {
        // Setup
        Product product = new Product("pear", 1.99, 10);

        // Invoke
        Product result = assertDoesNotThrow(() -> inventoryFileDAO.createProduct(product),
                                "Unexpected exception thrown");
        
        // Analyze
        assertNotNull(result);
        Product actual = inventoryFileDAO.getProduct(result.getName());
        assertEquals(actual.getName(), product.getName());
        assertEquals(actual.getPrice(), product.getPrice());
        assertEquals(actual.getQuantity(), product.getQuantity());
    }

    @Test
    public void testUpdateProduct() {
        // Setup
        Product product = new Product("apple", 50, 50);

        Product result = assertDoesNotThrow(() -> inventoryFileDAO.updateProduct(product),
            "Unexpected exception thrown");
        
        assertNotNull(result);
        Product actual = inventoryFileDAO.getProduct(product.getName());
        assertEquals(actual, product);
    }

    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class), any(Product[].class));

        Product product = new Product("dragonfruit", 3.99, 10);

        assertThrows(IOException.class,
                        () -> inventoryFileDAO.createProduct(product),
                        "IOException not thrown");
    }

    @Test
    public void testGetProductNotFound() {
        // Invoke
        Product product = inventoryFileDAO.getProduct("tomato");

        // Analyze
        assertEquals(product, null);
    }

    @Test
    public void testDeleteProductNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> inventoryFileDAO.deleteProduct("tomato"),
                                                "Unexpected exception thrown");
        
        // Analyze
        assertEquals(result, false);
        assertEquals(inventoryFileDAO.products.size(), testProducts.length);
    }

    @Test
    public void testUpdateHeroNotFound() {
        // Setup
        Product product = new Product("grapes", 3.99, 10);

        // Invoke
        Product result = assertDoesNotThrow(() -> inventoryFileDAO.updateProduct(product),
        "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the HeroFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
            .readValue(new File("doesn't_matter.txt"), Product[].class);

        // Invoke & Analyze
        assertThrows(IOException.class, () -> new InventoryFileDAO("doesn't_matter.txt", 
        mockObjectMapper), "IOException not thrown");

    }





}
