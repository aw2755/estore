package com.estore.api.estoreapi.model;

import java.util.ArrayList;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a user entity
 * 
 * @author Brian Lin
 */
public class User {
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    @JsonProperty("username") private String userName;
    private ArrayList<Product> cart;

    public User(@JsonProperty("username") String userName) {
        this.userName = userName;
        this.cart = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void addProduct(Product product) {
        this.cart.add(product);
    }

    public void deleteProduct(String productName) {
        for (Product product : this.cart) {
            if (productName.equals(product.getName())) {
                cart.remove(product);
            }
        }
    }

    public ArrayList<Product> getProducts() {
        return cart;
    }

    @Override
    public String toString() {
        return "User [userName=" + userName + ", cart=" + cart + "]";
    }


}
