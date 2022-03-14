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
    Map<String, User> Users; // Provides a local cache of the User objects
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
    public UserFileDAO(@Value("${inventory.file}") String filename, ObjectMapper objectMapper) throws IOException {
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
    private User[] getUserArray(String containsText) { // if containsText == null, no filter
        ArrayList<User> UserArrayList = new ArrayList<>();

        for (User User : users.values()) {
            if (containsText == null || user.getName().contains(containsText)) {
                UserArrayList.add(User);
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
        for (User User : UserArray) {
            users.put(user.getName().toLowerCase(), user);
        }

        return true;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User[] getUsers() {
        synchronized (Users) {
            return getUsersArray();
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User[] findUsers(String containsText) {
        synchronized (Users) {
            return getUsersArray(containsText);
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User getUser(String name) {
        synchronized (Users) {
            if (Users.containsKey(name))
                return Users.get(name);
            else
                return null;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User createUser(User User) throws IOException {
        synchronized (Users) {
            if (!(Users.containsKey(User.getName().toLowerCase()))) {
                User newUser = new User(User.getName(), User.getPrice(), User.getQuantity());
                Users.put(newUser.getName().toLowerCase(), newUser);
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
            if (Users.containsKey(User.getName()) == false)
                return null; // hero does not exist

            Users.put(user.getName(), user);
            save(); // may throw an IOException
            return User;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public boolean deleteUser(String name) throws IOException {
        synchronized (Users) {
            if (Users.containsKey(name)) {
                Users.remove(name);
                return save();
            } else
                return false;
        }
    }
}
