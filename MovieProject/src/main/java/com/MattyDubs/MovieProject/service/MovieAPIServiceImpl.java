package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.config.MovieConfig;
import com.MattyDubs.MovieProject.entity.Movie;
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
    public Movie getMovieByTitleAndYear(String title, String year) {
        return movieConfig.webClientBuilder(title, year).build().get().retrieve().bodyToMono(Movie.class).block();
    }

    @Override
    public Movie getMovieByTitle(String title) {
        return movieConfig.webClientBuilder(title).build().get().retrieve().bodyToMono(Movie.class).block();
    }
}
