package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Movie;

import java.util.List;

/**
 * MovieService class uses the MovieDAO implementation to interact with the DB and hold the basic business logic for
 * the movie entity. This class is part of the service layer to be used by the controller.
 */
public interface MovieService {
    void save(Movie movie, CustomUser user);

    void deleteMovie(int id);

    Movie findById(int id);

    List<Movie> findByTitleYear(String title, String year);

    List<Movie> findAll();

    List<Movie> findAllByUser(CustomUser user);

    List<Movie> findByTitleYearUser(String title, String year, CustomUser user);

    Movie singleFindByTitleYear(String title, String year);

    /**
     * saveMovieForUser method used to save a movie to the proper user. The method will check whether the user
     * has already saved the movie. If not, then it will call the movieDAO to save the movie to the DB.
     *
     * @param user  The current logged-in user.
     * @param movie The movie to be saved.
     * @return The users movie personal movie list.
     */
    List<Movie> saveMovieForUser(CustomUser user, Movie movie);

    void updateMovie(Movie movie);
}
