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
    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    static final String STRING_FORMAT = "User [id=%d, username=%s]";

    @JsonProperty("username") private String userName;
    public ArrayList<Product> cart;

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

    @Override
    public String toString() {
        return "User [userName=" + userName + ", cart=" + cart + "]";
    }


}
