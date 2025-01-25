package com.MattyDubs.MovieProject.controller;


import com.MattyDubs.MovieProject.entity.Movie;
import com.MattyDubs.MovieProject.entity.MovieSearch;
import com.MattyDubs.MovieProject.service.MovieAPIService;
import com.MattyDubs.MovieProject.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final MovieAPIService movieAPIService;

    @Autowired
    public MovieController(MovieService movieService, MovieAPIService movieAPIService) {
        this.movieService = movieService;
        this.movieAPIService = movieAPIService;
    }

    @GetMapping("/search")
    public String searchForm(Model model) {
        model.addAttribute("movieInfo", new MovieSearch());
        return "search-movies-test";
    }

    @GetMapping("/list")
    public String listMovies(Model model) {
        List<Movie> movies = movieService.findAll();
        System.out.println(movies.get(0).getImageURL());
        model.addAttribute("movies", movies);
        return "movie-list";
    }

    @PostMapping("/save")
    public String saveMovie(@ModelAttribute("movie") Movie movie) {
        List<Movie> movies = movieService.findByTitleYear(movie.getTitle(), movie.getYear());
        if (movies.isEmpty())
            movieService.save(movie);
        return "redirect:/movies/list";
    }

    @GetMapping("/deleteMovie")
    public String deleteMovie(@RequestParam("id") int id) {
        Movie movieToRemove = movieService.findById(id);
        if (movieToRemove != null)
            movieService.deleteMovie(movieToRemove);
        return "redirect:/movies/list";
    }

    @PostMapping("/showMovie")
    public String showMovie(@ModelAttribute("movieInfo") MovieSearch movieSearch, Model model) {
        Movie movie;
        if (!movieSearch.getYear().isBlank()) {
            movie = movieAPIService.getMovieByTitleAndYear(movieSearch.getTitle(), movieSearch.getYear());
            System.out.println(movie);
        } else {
            movie = movieAPIService.getMovieByTitle(movieSearch.getTitle());
            System.out.println(movie);
        }
        model.addAttribute("movie", movie);
        return "show-movie-test";
    }

    @GetMapping("/showMovieDetails")
    public String showMovieDetails(@RequestParam("title") String title, @RequestParam("year") String year, Model model) {
        Movie movie = movieAPIService.getMovieByTitleAndYear(title, year);
        model.addAttribute("movie", movie);
        return "show-movie-test";
    }
}
