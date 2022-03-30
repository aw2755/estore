package com.estore.api.estoreapi.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

import com.estore.api.estoreapi.persistence.InventoryDAO;
import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;

@RestController
@RequestMapping("user")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private UserDAO userDAO;
    private InventoryDAO inventoryDAO;
    

    public UserController(UserDAO userDAO, InventoryDAO inventoryDAO) {
        this.userDAO = userDAO;
        this.inventoryDAO = inventoryDAO;
    }

    @GetMapping("/{name}")
    public ResponseEntity<User> getUser(@PathVariable String name) {
        LOG.info("GET /user/ " + name);

        try {
            User user = userDAO.getUser(name);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error getting user", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<User[]> getUsers() throws Exception{
        LOG.info("GET /user ");
        try {
            User[] users = userDAO.getUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        LOG.info("POST /user/" + user);
        try {
            User newUser = userDAO.createUser(user);
            if (newUser == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{username}/cart/all")
    public ResponseEntity<ArrayList<Product>> getCartProducts(@PathVariable String username) {
        LOG.info("GET /" + username + "/cart");
        try {
            User selectedUser = userDAO.getUser(username);
            ArrayList<Product> products = selectedUser.getProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{username}/cart/add")
    public ResponseEntity<User> addProduct(@PathVariable String username, @RequestBody String name) {
        LOG.info("Adding " + name);
        try {
            User selectedUser = userDAO.getUser(username);
            selectedUser.addProduct(inventoryDAO.getProduct(name));
            User updated = userDAO.updateUser(selectedUser);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch(Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{username}/cart/remove/{name}")
    public ResponseEntity<User> deleteProduct(@PathVariable String username, @PathVariable String name) {
        LOG.info("DELETE /user/" + username + "/cart/remove/" + name);
        try {
            User selectedUser = userDAO.getUser(username);
            selectedUser.deleteProduct(name);
            User updated = userDAO.updateUser(selectedUser);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch(Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{username}/cart/checkout")
    public ResponseEntity<User> checkoutProducts(@PathVariable String username) {
        LOG.info("checkout");
        try {
            User selectedUser = userDAO.getUser(username);
            selectedUser.checkout();
            User updated = userDAO.updateUser(selectedUser);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch(Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
