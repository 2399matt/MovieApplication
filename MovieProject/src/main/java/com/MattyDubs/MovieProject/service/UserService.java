package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Movie;

import java.util.List;

/**
 * UserService class uses the UserDAO implementation to interact with the database. NOTE: this class is for our
 * CustomUser entity, not for Spring-Security.
 */
public interface UserService {

    CustomUser save(CustomUser user);

    CustomUser findByUsername(String username);

    CustomUser findUserAndMovies(String username);

    /**
     * updateMovieForUser method is used to update the watched status of a movie for a given user. Works with the
     * update methods in the MovieController class.
     *
     * @param user user for the current logged-in user.
     * @param title    Title of the movie to update.
     * @param year     Year that the movie was released.
     * @param status   Watched or unwatched status of the movie, string holds value of either true or false.
     */
    void updateMovieForUser(CustomUser user, String title, String year, String status);

    boolean checkUserExists(String username);

    List<Movie> getPagedMovies(CustomUser user, int page, int size);

}
