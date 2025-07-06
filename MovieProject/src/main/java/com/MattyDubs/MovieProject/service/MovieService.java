package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.entity.Movie;

import java.util.List;

/**
 * MovieService class uses the MovieDAO implementation to interact with the DB and hold the basic business logic for
 * the movie entity. This class is part of the service layer to be used by the controller.
 */
public interface MovieService {
    Movie save(Movie movie);

    void deleteMovie(int id);

    Movie findById(int id);

    List<Movie> findByTitleYear(String title, String year);

    List<Movie> findAll();


    Movie singleFindByTitleYear(String title, String year);

    void updateMovie(Movie movie);

}
