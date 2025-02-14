package com.MattyDubs.MovieProject.data;

import com.MattyDubs.MovieProject.dao.TopMoviesRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopMovies {

    @Autowired
    private TopMoviesRepo topMoviesRepo;

    private List<TopMovieModel> movies;

    /**
     * Loads the top 250 movies from the database on application start-up.
     * To be called in the controller.
     */
    @PostConstruct
    public void loadMovies() {
        this.movies = topMoviesRepo.findAll();
    }

    public List<TopMovieModel> getMovies() {
        return this.movies;
    }
}
