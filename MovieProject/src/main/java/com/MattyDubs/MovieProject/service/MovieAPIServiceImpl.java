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
        return movieConfig.getMovieWithYear(title, year, type).build().get().retrieve().body(MovieListContainer.class);
    }

    @Override
    public MovieListContainer getMovieByTitle(String title, String type) {
        return movieConfig.getMovieWithoutYear(title, type).build().get().retrieve().body(MovieListContainer.class);
    }

    @Override
    public Movie getSingleMovie(String title, String year) {
        return movieConfig.getSingleMovie(title, year).build().get().retrieve().body(Movie.class);
    }
}
