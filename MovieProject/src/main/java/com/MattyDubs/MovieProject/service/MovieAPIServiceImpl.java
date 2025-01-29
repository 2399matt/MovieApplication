package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.config.MovieConfig;
import com.MattyDubs.MovieProject.entity.Movie;
import com.MattyDubs.MovieProject.entity.MovieListContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieAPIServiceImpl implements MovieAPIService {

    /**
     * MovieConfig class is used to build the WebClient from our MovieConfig class
     * and retrieve the Movie object for our controller to use.
     */
    private final MovieConfig movieConfig;

    @Autowired
    public MovieAPIServiceImpl(MovieConfig movieConfig) {
        this.movieConfig = movieConfig;
    }

    @Override
    public MovieListContainer getMovieByTitleAndYear(String title, String year) {
        return movieConfig.webClientBuilder(title, year).build().get().retrieve().bodyToMono(MovieListContainer.class).block();
    }

    @Override
    public MovieListContainer getMovieByTitle(String title) {
        return movieConfig.webClientBuilder(title).build().get().retrieve().bodyToMono(MovieListContainer.class).block();
    }

    public Movie getSingleMovie(String title, String year){
        return movieConfig.getSingleMovie(title, year).build().get().retrieve().bodyToMono(Movie.class).block();
    }
}
