package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Product;

/**
 * Implements the functionality for JSON file-based peristance for Products
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Brian Lin, Tyrone Tha
 */
@Component
public class InventoryFileDAO implements InventoryDAO{
    private static final Logger LOG = Logger.getLogger(InventoryFileDAO.class.getName());
    Map<String, Product> products;      // Provides a local cache of the product objects
                                        // so that we don't need to read from the file
                                        // each time

    private ObjectMapper objectMapper;  // Provides conversion between Product
                                        // objects and JSON text format written
                                        // to the file

    private String filename;            // Filename to read from and write to

    /**
     * Creates a Product File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public InventoryFileDAO(@Value("${inventory.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();     // load the products from the file
    }

    /**
     * Generates an array of {@linkplain Product products} from the tree map
     * 
     * @return  The array of {@link Product products}, may be empty
     */
    private Product[] getProductsArray() {
        return getProductsArray(null);
    }

    /**
     * Generates an array of {@linkplain Product products} from the tree map for any
     * {@linkplain Product products} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Product products}
     * in the tree map
     * 
     * @return  The array of {@link Product product}, may be empty
     */
    private Product[] getProductsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Product> productArrayList = new ArrayList<>();

        for (Product product : products.values()) {
            if (containsText == null || product.getName().contains(containsText)) {
                productArrayList.add(product);
            }
        }

        Product[] productArray = new Product[productArrayList.size()];
        productArrayList.toArray(productArray);
        return productArray;
    }

    /**
     * Saves the {@linkplain Product products} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Product products} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Product[] productArray = getProductsArray();

        objectMapper.writeValue(new File(filename), productArray);
        return true;
    }

    /**
     * Loads {@linkplain Product products} from the JSON file into the map
     * <br>
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        products = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of products
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Product[] productArray = objectMapper.readValue(new File(filename), Product[].class);

        // Add each product to the tree map
        for (Product product : productArray) {
            products.put(product.getName().toLowerCase(), product);
        }

        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Product[] getProducts() {
        synchronized(products) {
            return getProductsArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Product[] findProducts(String containsText) {
        synchronized(products) {
            return getProductsArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Product getProduct(String name) {
        synchronized(products) {
            if (products.containsKey(name))
                return products.get(name);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Product createProduct(Product product) throws IOException {
        synchronized(products) {
            if (!(products.containsKey(product.getName().toLowerCase()))) {
                Product newProduct = new Product(product.getName(), product.getPrice(), product.getQuantity());
                products.put(newProduct.getName().toLowerCase(), newProduct);
                save(); // may throw an IOException
                return newProduct;
            }
            return null;
            
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Product updateProduct(Product product) throws IOException {
        synchronized(products) {
            if (products.containsKey(product.getName()) == false)
                return null;  // hero does not exist

            products.put(product.getName(),product);
            save(); // may throw an IOException
            return product;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteProduct(String name) throws IOException {
        synchronized(products) {
            if (products.containsKey(name)) {
                products.remove(name);
                return save();
            }
            else
                return false;
        }
    }
}
