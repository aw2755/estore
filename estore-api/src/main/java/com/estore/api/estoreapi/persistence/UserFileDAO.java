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

import com.estore.api.estoreapi.model.User;

/**
 * Implements the functionality for JSON file-based peristance for Users
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of
 * this
 * class and injects the instance into other classes as needed
 * 
 * @author Brian Lin, Tyrone Tha, Alan Wang, Diego Avila
 */
@Component
public class UserFileDAO implements UserDAO {
    private static final Logger LOG = Logger.getLogger(UserFileDAO.class.getName());
    Map<String, User> users; // Provides a local cache of the User objects
                             // so that we don't need to read from the file
                             // each time

    private ObjectMapper objectMapper; // Provides conversion between User
                                       // objects and JSON text format written
                                       // to the file

    private String filename; // Filename to read from and write to

    /**
     * Creates a User File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public UserFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(); // load the Users from the file
    }

    /**
     * Generates an array of {@linkplain User Users} from the tree map
     * 
     * @return The array of {@link User Users}, may be empty
     */
    private User[] getUsersArray() {
        return getUsersArray(null);
    }

    /**
     * Generates an array of {@linkplain User Users} from the tree map for any
     * {@linkplain User Users} that contains the text specified by
     * containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain User
     * Users}
     * in the tree map
     * 
     * @return The array of {@link User User}, may be empty
     */
    private User[] getUsersArray(String containsText) { // if containsText == null, no filter
        ArrayList<User> UserArrayList = new ArrayList<>();

        for (User user : users.values()) {
            if (containsText == null || user.getUserName().contains(containsText)) {
                UserArrayList.add(user);
            }
        }

        User[] UserArray = new User[UserArrayList.size()];
        UserArrayList.toArray(UserArray);
        return UserArray;
    }

    /**
     * Saves the {@linkplain User Users} from the map into the file as an
     * array of JSON objects
     * 
     * @return true if the {@link User Users} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        User[] UserArray = getUsersArray();

        objectMapper.writeValue(new File(filename), UserArray);
        return true;
    }

    /**
     * Loads {@linkplain User Users} from the JSON file into the map
     * <br>
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of Users
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        User[] UserArray = objectMapper.readValue(new File(filename), User[].class);

        // Add each User to the tree map
        for (User user : UserArray) {
            users.put(user.getUserName(), user);
        }

        return true;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User[] getUsers() {
        synchronized(users) {
            return getUsersArray();
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User[] findUsers(String name) {
        synchronized(users) {
            return getUsersArray(name);
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User getUser(String name) {
        synchronized(users) {
            if (users.containsKey(name))
                return users.get(name);
            else
                return null;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User createUser(User user) throws IOException {
        synchronized(users) {
            if (!(users.containsKey(user.getUserName()))) {
                User newUser = new User(user.getId(), user.getUserName());
                users.put(newUser.getUserName(), newUser);
                save(); // may throw an IOException
                return newUser;
            }
            return null;

        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User updateUser(User user) throws IOException {
        synchronized (users) {
            if (users.containsKey(user.getUserName()) == false)
                return null; // user does not exist

            users.put(user.getUserName(), user);
            save(); // may throw an IOException
            return user;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public boolean deleteUser(String name) throws IOException {
        synchronized(users) {
            if (users.containsKey(name)) {
                users.remove(name);
                return save();
            } else
                return false;
        }
    }
}
