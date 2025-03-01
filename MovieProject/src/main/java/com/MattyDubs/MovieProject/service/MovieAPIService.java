package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.entity.Movie;
import com.MattyDubs.MovieProject.entity.MovieListContainer;
import reactor.core.publisher.Mono;

public interface MovieAPIService {

    MovieListContainer getMovieByTitleAndYear(String title, String year, String type);

    MovieListContainer getMovieByTitle(String title, String type);

    Movie getSingleMovie(String title, String year);

}
