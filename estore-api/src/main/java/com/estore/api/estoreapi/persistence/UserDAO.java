package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.User;

public interface UserDAO {

    User[] getUsers() throws Exception;

    User[] findUsers(String name) throws IOException;

    User getUser(String name) throws IOException;

    User createUser(User user) throws IOException;

    User updateUser(User user) throws IOException;

}
