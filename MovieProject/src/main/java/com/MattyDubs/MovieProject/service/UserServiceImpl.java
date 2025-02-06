package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.dao.UserDAO;
import com.MattyDubs.MovieProject.entity.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    /**
     * UserService class that uses a userDAO object to interact with the DB
     * THIS IS ONLY FOR CustomUsers!! Not related to Spring-Security.
     */
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public CustomUser save(CustomUser user) {
        return userDAO.save(user);
    }

    @Override
    public CustomUser findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public CustomUser findUserAndMovies(String username) {
        return userDAO.findUserAndMovies(username);
    }
}
