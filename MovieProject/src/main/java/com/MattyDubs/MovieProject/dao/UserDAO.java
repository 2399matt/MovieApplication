package com.MattyDubs.MovieProject.dao;

import com.MattyDubs.MovieProject.entity.CustomUser;

import java.util.List;

/**
 * userDAO class for interacting with the DB. This is for CustomUsers, not for Spring-Security users.
 * Basic DB methods for finding/saving the user. Custom queries for finding a user and fetching their movies.
 * userDAO will be used by the UserService class in the service layer.
 */
public interface UserDAO {

    CustomUser save(CustomUser user);

    CustomUser findByUsername(String username);

    CustomUser findUserAndMovies(String username);

    List<CustomUser> findAll();

    boolean checkUserExists(String username);

    CustomUser getUserAndMovies(CustomUser user);

    CustomUser findByToken(String token);
}
