package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.entity.Movie;
import com.MattyDubs.MovieProject.entity.MovieListContainer;

/**
 * Service class that uses the MovieConfig class to interact with the OMDB API.
 * This class retrieves the webclient builders from the config class and builds them to send the request, and map
 * the results into our Movie / MovieList objects.
 */
public interface MovieAPIService {

    /**
     * Used to get a list of movies from the API. Will match movies based on the title / type. To be used on
     * the search result webpage.
     *
     * @param title Title of the movie.
     * @param year  Year that the movie was released.
     * @param type  Movie/series
     * @return A list of movies held in the MoveListContainer.
     */
    MovieListContainer getMovieByTitleAndYear(String title, String year, String type);

    MovieListContainer getMovieByTitle(String title, String type);

    /**
     * Used to get a single movie object from the API.
     *
     * @param title Title of the movie.
     * @param year  Yeaer that the movie was released.
     * @return A movie object mapped from JSON response.
     */
    Movie getSingleMovie(String title, String year);

}
