package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.config.MovieConfig;
import com.MattyDubs.MovieProject.entity.Movie;
import com.MattyDubs.MovieProject.entity.MovieListContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MovieAPIServiceImpl implements MovieAPIService {

    private final MovieConfig movieConfig;

    @Autowired
    public MovieAPIServiceImpl(MovieConfig movieConfig) {
        this.movieConfig = movieConfig;
    }

    @Override
    public MovieListContainer getMovieByTitleAndYear(String title, String year, String type) {
        return movieConfig.webClientBuilder(title, year, type).build().get().retrieve().bodyToMono(MovieListContainer.class).block();
    }

    @Override
    public MovieListContainer getMovieByTitle(String title, String type) {
        return movieConfig.webClientBuilder(title, type).build().get().retrieve().bodyToMono(MovieListContainer.class).block();
    }

    public Movie getSingleMovie(String title, String year) {
        return movieConfig.getSingleMovie(title, year).build().get().retrieve().bodyToMono(Movie.class).block();
    }
}
