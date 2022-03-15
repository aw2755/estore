package com.estore.api.estoreapi.model;

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

    //@JsonProperty("id") private int id;
    @JsonProperty("username") private String userName;
    @JsonProperty("username") private String password;
    @JsonProperty("username") private String role;

    public User(@JsonProperty("username") String userName, @JsonProperty("password") String password) {
        
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    
}
