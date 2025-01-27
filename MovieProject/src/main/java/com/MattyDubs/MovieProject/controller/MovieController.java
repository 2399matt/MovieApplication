package com.MattyDubs.MovieProject.controller;


import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Movie;
import com.MattyDubs.MovieProject.entity.MovieSearch;
import com.MattyDubs.MovieProject.service.MovieAPIService;
import com.MattyDubs.MovieProject.service.MovieService;
import com.MattyDubs.MovieProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final MovieAPIService movieAPIService;
    private final UserService userService;

    @Autowired
    public MovieController(MovieService movieService, MovieAPIService movieAPIService, UserService userService) {
        this.movieService = movieService;
        this.movieAPIService = movieAPIService;
        this.userService = userService;
    }

    /**
     * Search endpoint, returns our search HTML page where the user can create the MovieSearch object
     * with the title/year of the desired movie.
     * @param model The model that the controller uses to talk to the webpage.
     * @return the search movies page.
     */
    @GetMapping("/search")
    public String searchForm(Model model) {
        model.addAttribute("movieInfo", new MovieSearch());
        return "search-movies-test";
    }

    /**
     * List endpoint, used to list all the movies that the logged-in user has saved.
     * @param model The model.
     * @return the list-webpage that holds all the user's saved movies.
     */
    @GetMapping("/list")
    public String listMovies(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUser user = userService.findByUsername(auth.getName());
        List<Movie> movies = movieService.findAllByUser(user);
        model.addAttribute("movies", movies);
        return "movie-list";
    }

    /**
     * Post-handling for saving a movie to the DB. We use Authentication to get the current user's username.
     * Need to create the CustomUser so that the movie-user relationship is maintained.
     * @param movie The movie to be saved, requested from the model
     * @return The user back to the list, with the new movie added.
     */
    @PostMapping("/save")
    public String saveMovie(@ModelAttribute("movie") Movie movie) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUser user = userService.findByUsername(auth.getName());
        movie.setUser(user);
        movieService.save(movie, user);
        userService.save(user);

        return "redirect:/movies/list";
    }

    /**
     * Delete endpoint, used to remove a movie from the user's list. Triggered on button-click.
     * @param id id of the movie to be deleted.
     * @return the user back to the list, with the deleted movie gone.
     */
    @GetMapping("/deleteMovie")
    public String deleteMovie(@RequestParam("id") int id) {
        Movie movieToRemove = movieService.findById(id);
        if (movieToRemove != null)
            movieService.deleteMovie(movieToRemove);
        return "redirect:/movies/list";
    }

    /**
     * ShowMovie endpoint is reached after the user has searched for a movie. Takes in the MovieSearch object
     * that contains the title/year, and calls the API to retrieve a Movie object for that movie.
     * Movie is added to the model and the information is displayed on show-movie.
     * @param movieSearch The MovieSearch object that holds the title/year of the movie.
     * @param model The model.
     * @return webpage to display the movie information.
     */
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

    /**
     * Similar to the ShowMovie, however, this endpoint is accessed from the user's list. We request the params
     * needed to make a movie search with the API.
     * @param title title of the movie
     * @param year year that the movie was released
     * @param model the model
     * @return show-movie webpage, same as in the showMovie method.
     */
    @GetMapping("/showMovieDetails")
    public String showMovieDetails(@RequestParam("title") String title, @RequestParam("year") String year, Model model) {
        Movie movie = movieAPIService.getMovieByTitleAndYear(title, year);
        model.addAttribute("movie", movie);
        return "show-movie-test";
    }
}
