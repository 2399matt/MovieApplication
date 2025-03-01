package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.dao.TopMoviesRepo;
import com.MattyDubs.MovieProject.data.TopMovieModel;
import com.MattyDubs.MovieProject.data.TopMovies;
import com.MattyDubs.MovieProject.entity.Movie;
import com.MattyDubs.MovieProject.util.MovieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MovieCheckService class is used to check if a movie exists in the DB before resorting to an API call.
 * The topMovies list is checked, as well as the movies table. If neither turn up a result, the API is called.
 * To be used by the MovieController.
 */
@Component
public class MovieCheckService {

    private final MovieUtil movieUtil;
    private final MovieService movieService;
    private final MovieAPIService movieAPIService;
    private final TopMovies topMovies;

    @Autowired
    public MovieCheckService(TopMoviesRepo topMoviesRepo, MovieUtil movieUtil, MovieService movieService, MovieAPIService movieAPIService, TopMovies topMovies) {
        this.movieUtil = movieUtil;
        this.movieService = movieService;
        this.movieAPIService = movieAPIService;
        this.topMovies = topMovies;
    }

    public Movie checkMovieExistence(String title, String year) {
        TopMovieModel tm = topMovies.getMovies().stream()
                .filter(m -> m.getTitle().equals(title) && m.getYear().equals(year))
                .findFirst()
                .orElse(null);
        if (tm != null) {
            System.out.println("Found with top-movie DB!");
            return movieUtil.mapFromTopMovieModel(tm);
        } else {
            Movie movie = movieService.singleFindByTitleYear(title, year);
            if (movie != null) {
                System.out.println("Found with movie-db!");
                return movieUtil.copyMovie(movie);
            } else {
                System.out.println("Found with API!");
                return movieAPIService.getSingleMovie(title, year);
            }
        }

    }
}
