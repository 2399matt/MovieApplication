package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.UserMovies;

import java.util.List;

/**
 * UserService class uses the UserDAO implementation to interact with the database. NOTE: this class is for our
 * CustomUser entity, not for Spring-Security.
 */
public interface UserService {

    CustomUser save(CustomUser user);

    CustomUser findByUsername(String username);

    boolean checkUserExists(String username);

    List<UserMovies> getPagedMovies(CustomUser user, int page, int size);

    CustomUser getUserAndMovies(CustomUser user);

}
