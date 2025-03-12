package com.MattyDubs.MovieProject.data;

import com.MattyDubs.MovieProject.dao.TopMoviesRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Top movies helper class. Loads the top movies into memory at application start-up.
 * Methods to retrieve the entire list, or filter the list by genre.
 */
@Component
public class TopMovies {

    private final TopMoviesRepo topMoviesRepo;

    private List<TopMovieModel> movies;

    @Autowired
    public TopMovies(TopMoviesRepo topMoviesRepo) {
        this.topMoviesRepo = topMoviesRepo;
    }

    /**
     * Loads the top 250 movies from the database on application start-up.
     * To be utilized in the controller.
     */
    @PostConstruct
    public void loadMovies() {
        this.movies = topMoviesRepo.findAll();
    }

    public List<TopMovieModel> getMovies() {
        return this.movies;
    }

    public List<TopMovieModel> getByGenre(String genre) {
        return this.movies
                .stream()
                .filter(movie -> movie.getGenre().toLowerCase().contains(genre))
                .toList();
    }

    public List<TopMovieModel> getPagedTopMovies(Integer page, String genre) {
        List<TopMovieModel> allMovies;
        if (genre == null) {
            allMovies = getMovies();
        } else {
            allMovies = getByGenre(genre);
        }
        int pageSize = 10;
        int totalMovies = allMovies.size();

        // Handle pagination logic
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalMovies);

        return allMovies.subList(start, end);
    }
}
