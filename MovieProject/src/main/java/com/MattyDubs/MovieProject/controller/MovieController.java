package com.MattyDubs.MovieProject.controller;

import com.MattyDubs.MovieProject.dao.TopMoviesRepo;
import com.MattyDubs.MovieProject.data.TopMovieModel;
import com.MattyDubs.MovieProject.data.TopMovies;
import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Movie;
import com.MattyDubs.MovieProject.entity.MovieListContainer;
import com.MattyDubs.MovieProject.entity.MovieSearch;
import com.MattyDubs.MovieProject.service.MovieAPIService;
import com.MattyDubs.MovieProject.service.MovieService;
import com.MattyDubs.MovieProject.service.UserService;
import com.MattyDubs.MovieProject.util.MovieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieAPIService movieAPIService;
    private final UserService userService;
    private final TopMovies topMovies;
    private final TopMoviesRepo topMoviesRepo;
    private final MovieUtil movieUtil;

    @Autowired
    public MovieController(MovieService movieService, MovieAPIService movieAPIService, UserService userService,
                           TopMovies topMovies, TopMoviesRepo topMoviesRepo, MovieUtil movieUtil) {
        this.movieService = movieService;
        this.movieAPIService = movieAPIService;
        this.userService = userService;
        this.topMovies = topMovies;
        this.topMoviesRepo = topMoviesRepo;
        this.movieUtil = movieUtil;
    }

    /**
     * Search endpoint, returns our search HTML page where the user can create the MovieSearch object
     * with the title/year of the desired movie.
     *
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
     *
     * @param model The model.
     * @return the list-webpage that holds all the user's saved movies.
     */
    @GetMapping("/list")
    public String listMovies(Model model, Principal principal) {
        //CustomUser user = userService.findByUsername(principal.getName());

        CustomUser user = userService.findUserAndMovies(principal.getName());
        model.addAttribute("movies", user.getMovies());

        // List<Movie> movies = movieService.findAllByUser(user);
        // model.addAttribute("movies", movies);
        return "movie-list";
    }

    /**
     * Post-handling for saving a movie to the DB. We use Authentication to get the current user's username.
     * Need to create the CustomUser so that the movie-user relationship is maintained.
     *
     * @param movie The movie to be saved, requested from the model
     * @return The user back to the list, with the new movie added.
     */
    @PostMapping("/save")
    public String saveMovie(@ModelAttribute("movie") Movie movie, Principal principal) {
        CustomUser user = userService.findUserAndMovies(principal.getName());
        Movie movieCheck = user.getMovies().stream()
                .filter(n -> n.getTitle().equals(movie.getTitle()) && n.getYear().equals(movie.getYear()))
                .findFirst()
                .orElse(null);
        if (movieCheck != null) {
            return "redirect:/movies/list";
        }
        movie.setUser(user);
        user.getMovies().add(movie); // Not really necessary, just for memory storage of movies.
        movieService.save(movie, user);
        userService.save(user);
        return "redirect:/movies/list";
    }

    /**
     * Delete endpoint, used to remove a movie from the user's list. Triggered on button-click.
     *
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
     * that contains the title/year, and calls the API to retrieve a list of matching movie objects.
     * Movies list is added to the model and the information is displayed on search list.
     *
     * @param movieSearch The MovieSearch object that holds the title/year of the movie.
     * @param model       The model.
     * @return webpage to display the movie information.
     */
    @PostMapping("/showMovie")
    public String showMovie(@ModelAttribute("movieInfo") MovieSearch movieSearch,
                            Model model, @RequestParam("type") String type) {
        MovieListContainer movieListContainer;
        if (!movieSearch.getYear().isBlank()) {
            movieListContainer = movieAPIService.getMovieByTitleAndYear(movieSearch.getTitle(), movieSearch.getYear(), type);
        } else {
            movieListContainer = movieAPIService.getMovieByTitle(movieSearch.getTitle(), type);
        }
        List<Movie> movies = movieListContainer.getMovies();
        model.addAttribute("movies", movies);
        return "search-movie-list";
    }

    /**
     * Similar to the ShowMovie, however, this endpoint is accessed from the user's list. We request the params
     * needed to make a movie search with the API.
     *
     * @param title title of the movie
     * @param year  year that the movie was released
     * @param model the model
     * @return show-movie webpage, display detailed info on a single movie.
     */
    @GetMapping("/showMovieDetails")
    public String showMovieDetails(@RequestParam("title") String title, @RequestParam("year") String year, Model model) {
        TopMovieModel tm = topMoviesRepo.findByTitle(title);
        if (tm != null) {
            Movie movie = movieUtil.mapFromTopMovieModel(tm);
            System.out.println("Movie found in top-movies DB!");
            model.addAttribute("movie", movie);
            return "show-movie-test";
        } else {
            Movie movie = movieService.singleFindByTitleYear(title, year);
            if (movie != null) {
                Movie newMovie = movieUtil.copyMovie(movie);
                System.out.println("Movie found in movies DB!");
                model.addAttribute("movie", newMovie);
                return "show-movie-test";
            } else {
                movie = movieAPIService.getSingleMovie(title, year);
                model.addAttribute("movie", movie);
                System.out.println("Found with API!");
                return "show-movie-test";
            }
        }
    }

    @GetMapping("/browse")
    public String showTopMovies(@RequestParam(required = false, name = "genre") String genre, @RequestParam(defaultValue = "1") int page, Model model) {
        System.out.println(genre);
        List<TopMovieModel> allMovies = new ArrayList<>();
        if (genre == null) {
            allMovies = topMovies.getMovies();
        } else {
            allMovies = topMovies.getMovies()
                    .stream()
                    .filter(n -> n.getGenre().toLowerCase().contains(genre))
                    .toList();
        }
        int pageSize = 10;
        int totalMovies = allMovies.size();
        int totalPages = (int) Math.ceil((double) totalMovies / pageSize);

        // Handle pagination logic
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalMovies);

        List<TopMovieModel> moviesPage = allMovies.subList(start, end);

        // Add the necessary attributes for the view
        model.addAttribute("movies", moviesPage);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("genre", genre);
        return "browse-movies";
    }

}
