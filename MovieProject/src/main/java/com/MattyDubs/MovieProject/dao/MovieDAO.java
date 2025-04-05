package com.MattyDubs.MovieProject.dao;


import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Movie;

import java.util.List;

/**
 * MovieDAO class used to interact with the movie table in our DB.
 * basic methods for saving, removing, and finding movies based on different needs.
 * MovieDAO will be used in MovieService, which will be sent to the controller.
 */
public interface MovieDAO {

    void save(Movie movie, CustomUser user);

    void deleteMovie(Movie movie);

    Movie findById(int id);

    List<Movie> findByTitleYear(String title, String year);

    List<Movie> findAll();

    List<Movie> findAllByUser(CustomUser user);

    List<Movie> findByTitleYearUser(String title, String year, CustomUser user);

    Movie singleFindByTitleYear(String title, String year);

    void updateMovie(Movie movie);

    boolean movieUserCheck(CustomUser user, String title);

}
