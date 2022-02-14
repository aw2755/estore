package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.InventoryDAO;
import com.estore.api.estoreapi.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Response;

/**
 * Test the Inventory Controller class
 * 
 * @author Brian Lin
 */
@Tag("Controller-tier")
public class InventoryControllerTest {
    private InventoryController inventoryController;
    private InventoryDAO mockInventoryDAO;

    /**
     * Before each test, create a new InventoryController object and inject
     * a mock Inventory DAO
     */
    @BeforeEach
    public void setupInventoryController() {
        mockInventoryDAO = mock(InventoryDAO.class);
        inventoryController = new InventoryController(mockInventoryDAO);
    }

    @Test
    public void testGetProduct() throws IOException {
        // Setup
        Product product = new Product("apple", 5.99, 10);
        // When the same name is passed in, our mock Inventory DAO will return the Product object
        when(mockInventoryDAO.getProduct(product.getName())).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = inventoryController.getProduct(product.getName());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testGetProductNotFound() throws Exception {
        // Setup
        String productName = "dragonfruit";
        
        // When the same id is passed in, our mock Inventory DAO will return null, simulating
        // no product found
        when(mockInventoryDAO.getProduct(productName)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = inventoryController.getProduct(productName);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetProductHandleException() throws Exception {
        // Setup
        String productName = "dragonfruit";

        // When getProduct is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).getProduct(productName);

        // Invoke
        ResponseEntity<Product> response = inventoryController.getProduct(productName);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateProduct() throws IOException {
        Product product = new Product("lemon", 4.99, 50);
        // when createProduct is called, return true simulating successful
        // creation and save
        when(mockInventoryDAO.createProduct(product)).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = inventoryController.createProduct(product);

        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    @Test
    public void testCreateProductFailed() throws IOException {
        // Setup
        Product product = new Product("apple", 9.99, 10);
         // when createProduct is called, return false simulating failed
        // creation and save
        when(mockInventoryDAO.createProduct(product)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = inventoryController.createProduct(product);
        
        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateProductHandleException() throws IOException {
        // Setup
        Product product = new Product("lime", 9.99, 10);

        // When createProduct is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).createProduct(product);

        // Invoke
        ResponseEntity<Product> response = inventoryController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateProduct() throws IOException {
        // Setup
        Product product = new Product("apple", 6.99, 50);
        // when updateProduct is called, return true simulating successful
        // update and save
        when(mockInventoryDAO.updateProduct(product)).thenReturn(product);
        ResponseEntity<Product> response = inventoryController.updateProduct(product);
        product.setPrice(4.99);

        // Invoke
        response = inventoryController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    @Test
    public void testUpdateProductFailed() throws IOException {
        // Setup
        Product product = new Product("lime", 4.99, 20);
        // when updateProduct is called, return true simulating successful
        // update and save
        when(mockInventoryDAO.updateProduct(product)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = inventoryController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateProductHandleExecption() throws IOException {
        // Setup
        Product product = new Product("lime", 4.99, 20);
        // When updateProduct is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).updateProduct(product);

        // Invoke
        ResponseEntity<Product> response = inventoryController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetProducts() throws IOException {
        // Setup
        Product[] products = new Product[2];
        products[0] = new Product("apple", 10, 10);
        products[1] = new Product("orange", 10, 20);
        // When getProducts is called return the products created above
        when(mockInventoryDAO.getProducts()).thenReturn(products);

        // Invoke
        ResponseEntity<Product[]> response = inventoryController.getProducts();
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(products,response.getBody());
    }

    @Test
    public void testGetProductsHandleException() throws IOException {
        // Setup
        // When getProducts is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).getProducts();

        // Invoke
        ResponseEntity<Product[]> response = inventoryController.getProducts();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchProducts() throws IOException {
        // Setup
        String searchString = "ra";
        Product[] products = new Product[2];
        products[0] = new Product("Grape", 10, 20);
        products[1] = new Product("Grapefruit", 5, 20);
        // when findProducts is called with the search string, return the two
        // heroes above
        when(mockInventoryDAO.findProducts(searchString)).thenReturn(products);

        // Invoke
        ResponseEntity<Product[]> response = inventoryController.searchProducts(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(products,response.getBody());
    }

    @Test
    public void testSearchProductsHandleException() throws IOException {
        // Setup
        String searchString = "an";
        // When createProduct is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).findProducts(searchString);

        // Invoke
        ResponseEntity<Product[]> response = inventoryController.searchProducts(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteProduct() throws IOException {
        // Setup
        String productName = "apple";
        // when deleteProduct is called return true, simulating successful deletion
        when(mockInventoryDAO.deleteProduct(productName)).thenReturn(true);

        // Invoke
        ResponseEntity<Product> response = inventoryController.deleteProduct(productName);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteProductNotFound() throws IOException {
        // Setup
        String productName = "apple";
        // when deleteProduct is called return true, simulating successful deletion
        when(mockInventoryDAO.deleteProduct(productName)).thenReturn(false);

        // Invoke
        ResponseEntity<Product> response = inventoryController.deleteProduct(productName);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteProductHandleException() throws IOException {
        // Setup
        String productName = "apple";
        // when deleteProduct is called return true, simulating successful deletion
        doThrow(new IOException()).when(mockInventoryDAO).deleteProduct(productName);

        // Invoke
        ResponseEntity<Product> response = inventoryController.deleteProduct(productName);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
