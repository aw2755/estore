package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Product;

public interface InventoryDAO {
    /**
     * Retrieves all {@linkplain Product products}
     * 
     * @return An array of {@link Product product} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product[] getProducts() throws IOException;

    /**
     * Finds all {@linkplain Product product} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Product product} whose names contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product[] findProducts(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Product product} with the given name
     * 
     * @param name The name of the {@link Product product} to get
     * 
     * @return a {@link Product product} object with the matching name
     * <br>
     * null if no {@link Product product} with a matching name is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product getProduct(String name) throws IOException;

    /**
     * Creates and saves a {@linkplain Product product}
     * 
     * @param product {@linkplain Product product} object to be created and saved
     * <br>
     *
     * @return new {@link Product product} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product createProduct(Product product) throws IOException;

    /**
     * Updates and saves a {@linkplain Product product}
     * 
     * @param {@link Product product} object to be updated and saved
     * 
     * @return updated {@link Product product} if successful, null if
     * {@link Product product} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product updateProduct(Product product) throws IOException;

    /**
     * Deletes a {@linkplain Product product} with the given name
     * 
     * @param name The name of the {@link Product product}
     * 
     * @return true if the {@link Product product} was deleted
     * <br>
     * false if product with the given name does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteProduct(String name) throws IOException;
}
