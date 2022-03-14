package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.User;

public interface UserDAO {
    /**
     * Retrieves all {@linkplain User Users}
     * 
     * @return An array of {@link User User} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage Users
     */
    User[] getUsers() throws IOException;

    /**
     * Finds all {@linkplain User User} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link User User} whose names contains the given text,
     *         may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] findUsers(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain User User} with the given name
     * 
     * @param name The name of the {@link User User} to get
     * 
     * @return a {@link User User} object with the matching name
     *         <br>
     *         null if no {@link User User} with a matching name is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    User getUser(String name) throws IOException;

    /**
     * Creates and saves a {@linkplain User User}
     * 
     * @param User {@linkplain User User} object to be created and saved
     *             <br>
     *
     * @return new {@link User User} if successful, false otherwise
     * 
     * @throws IOException if an issue with underlying storage
     */
    User createUser(User User) throws IOException;

    /**
     * Updates and saves a {@linkplain User User}
     * 
     * @param {@link User User} object to be updated and saved
     * 
     * @return updated {@link User User} if successful, null if
     *         {@link User User} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    User updateUser(User User) throws IOException;

    /**
     * Deletes a {@linkplain User User} with the given name
     * 
     * @param name The name of the {@link User User}
     * 
     * @return true if the {@link User User} was deleted
     *         <br>
     *         false if User with the given name does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteUser(String name) throws IOException;
}
