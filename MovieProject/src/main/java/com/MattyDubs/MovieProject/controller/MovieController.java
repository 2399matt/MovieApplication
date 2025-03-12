package com.MattyDubs.MovieProject.controller;

import com.MattyDubs.MovieProject.data.TopMovieModel;
import com.MattyDubs.MovieProject.data.TopMovies;
import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Movie;
import com.MattyDubs.MovieProject.entity.MovieListContainer;
import com.MattyDubs.MovieProject.entity.MovieSearch;
import com.MattyDubs.MovieProject.service.MovieAPIService;
import com.MattyDubs.MovieProject.service.MovieCheckService;
import com.MattyDubs.MovieProject.service.MovieService;
import com.MattyDubs.MovieProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


/**
 * MovieController class in charge of handling all movie-related endpoints.
 */
@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieAPIService movieAPIService;
    private final UserService userService;
    private final TopMovies topMovies;
    private final MovieCheckService movieCheckService;

    @Autowired
    public MovieController(MovieService movieService, MovieAPIService movieAPIService, UserService userService,
                           TopMovies topMovies, MovieCheckService movieCheckService) {
        this.movieService = movieService;
        this.movieAPIService = movieAPIService;
        this.userService = userService;
        this.topMovies = topMovies;
        this.movieCheckService = movieCheckService;
    }

    /**
     * Search endpoint, returns our search form HTML fragment to be placed on the homepage
     * Adds a MovieSearch object to the model for holding user input
     *
     * @param model The model that the controller uses to talk to the webpage.
     * @return the search movies page.
     */
    @GetMapping("/searchMovies")
    public String searchInsert(Model model) {
        model.addAttribute("movieInfo", new MovieSearch());
        return "/fragments/search-frag :: search-frag";
    }

    /**
     * homePage method used to return the homepage of the application.
     * Only method in the controller that returns a FULL view.
     *
     * @return The HTML document for our homepage.
     */
    @GetMapping("/home")
    public String homePage() {
        return "search-movies-test";
    }

    /**
     * List endpoint, used to list all the movies that the logged-in user has saved.
     * Calls on UserService to fetch the user and the users movies with the Principal obj.
     *
     * @param model The model.
     * @return The HTML fragment for the users personal movie-list page.
     */
    @GetMapping("/list")
    public String listMovies(@RequestParam(defaultValue = "10", name = "size") int size,
                             @RequestParam(defaultValue = "1", name = "page") Integer page, Model model, Principal principal) {
        CustomUser user = userService.findUserAndMovies(principal.getName());
//        int totalPages = (int) Math.ceil((double) user.getMovies().size() / size);
//        List<Movie> userMovies = userService.getPagedMovies(user, page, size);
//        model.addAttribute("movies", userMovies);
//        model.addAttribute("page", page);
//        model.addAttribute("totalPages", totalPages);
//        return "/fragments/personal-list :: movie-list";
        return returnListFragment(user, page, model);
    }

    /**
     * SaveMovie endpoint used to save a movie to the users movie-list.
     * Calls on UserService to find the current user with Principal and fetch the movie list.
     * Calls movieService to check if the user already saved the movie, if not, it will handle the save.
     *
     * @param movie The movie to be saved, requested from the model
     * @return The HTML fragment for the users personal movie-list page.
     */
    @PostMapping("/save")
    public String saveMovie(@ModelAttribute("movie") Movie movie, Principal principal, Model model) {
        CustomUser user = userService.findUserAndMovies(principal.getName());
        movieService.saveMovieForUser(user, movie);
        int page = 1;
        return returnListFragment(user, page, model);
    }

    /**
     * Delete endpoint, used to remove a movie from the user's list. Triggered on button-click.
     *
     * @param id id of the movie to be deleted.
     * @return the user back to the list, with the deleted movie gone.
     */
    @GetMapping("/deleteMovie")
    public String deleteMovie(@RequestParam(defaultValue = "1", name = "page") Integer page, @RequestParam("id") int id, Model model, Principal principal) {
        movieService.deleteMovie(id);
        CustomUser user = userService.findUserAndMovies(principal.getName());
        return returnListFragment(user, page, model);
    }

    /**
     * ShowMovie endpoint is reached after the user has searched for a movie. Takes in the MovieSearch object
     * that contains the title/year, and calls the API to retrieve a list of matching movie objects.
     * Movies list is added to the model and the information is displayed in a card-style search result.
     *
     * @param movieSearch The MovieSearch object that holds the title/year of the movie.
     * @param type        The type of search (movie, series).
     * @param model       The model.
     * @return HTML fragment for the search results.
     */
    @PostMapping("/showMovie")
    public String showMovie(@ModelAttribute("movieInfo") MovieSearch movieSearch,
                            @RequestParam("type") String type, Model model) {
        MovieListContainer movieListContainer;
        if (!movieSearch.getYear().isBlank()) {
            movieListContainer = movieAPIService.getMovieByTitleAndYear(movieSearch.getTitle(), movieSearch.getYear(), type);
        } else {
            movieListContainer = movieAPIService.getMovieByTitle(movieSearch.getTitle(), type);
        }
        List<Movie> movies = movieListContainer.getMovies();
        model.addAttribute("movies", movies);
        return "fragments/movie-lister :: movieResults";
    }

    /**
     * Similar to the ShowMovie, however, this endpoint is accessed from the user's list/browse page. We request the params
     * needed to make search for the movie in our tables, and resort to the API if it is not found.
     * Users will have the option to save the movie to their list from this page.
     *
     * @param title title of the movie
     * @param year  year that the movie was released
     * @param model the model
     * @return show-movie HTML fragment, used to display many details on a single movie.
     */
    @GetMapping("/showMovieDetails")
    public String showMovieDetails(@RequestParam("title") String title, @RequestParam("year") String year,
                                   @RequestParam(required = false, name = "page") Integer page, Model model) {
        Movie movie = movieCheckService.checkMovieExistence(title, year);
        model.addAttribute("movie", movie);
        model.addAttribute("page", page);
        return "fragments/show-movie :: show-movie";
    }

    /**
     * Browse endpoint. Used to display a paginated, and filterable list of the top 250 imdb movies.
     * Users have the option to show additional details for each movie in the list.
     *
     * @param genre The genre to be filtered by (not required).
     * @param page  The page number, used for pagination (required, default value).
     * @param model the model.
     * @return HTML fragment for the browse page. Movies are placed in a table.
     */
    @GetMapping("/browse")
    public String showTopMovies(@RequestParam(required = false, name = "browseGenre") String genre, @RequestParam(defaultValue = "1", name = "browsePage") Integer page, Model model) {
        int totalPages;
        int pageSize = 10;
        List<TopMovieModel> moviesPage = topMovies.getPagedTopMovies(page, genre);
        if (genre == null) {
            totalPages = (int) Math.ceil((double) topMovies.getMovies().size() / pageSize);
        } else {
            totalPages = (int) Math.ceil((double) topMovies.getByGenre(genre).size() / pageSize);
        }
        model.addAttribute("movies", moviesPage);
        model.addAttribute("browsePage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("browseGenre", genre);
        return "/fragments/browse-frag :: browse-frag";
    }

    /**
     * updateForm endpoint that returns the form needed to update a movies watch status. The form will contain
     * the title/year of the movie, as well as a status variable that holds true/false. POST request is sent to
     * updateWatchedStatus
     *
     * @param title title of the movie.
     * @param year  year the movie was released.
     * @param model the model.
     * @return HTML fragment for the update form. Will be placed where the button is in the table when clicked.
     */
    @GetMapping("/updateForm")
    public String updateForm(@RequestParam(defaultValue = "1", name = "page") Integer page, @RequestParam("title") String title, @RequestParam("year") String year, Model model) {
        model.addAttribute("title", title);
        model.addAttribute("year", year);
        model.addAttribute("page", page);
        return "/fragments/updateForm :: updateForm";
    }

    /**
     * updateWatchedStatus takes in information from the updateForm to update a movies watched status for the user.
     * Calls on userService to update the movie for the specific user.
     *
     * @param title     Title of the movie.
     * @param year      Year the movie was released.
     * @param status    The status chosen by the user (watched = true, not watched = false).
     * @param principal Principal class to retrieve the username of the current logged-in user.
     * @param model     the model.
     * @return HTML fragment for the personal movie-list page. Watched status will be updated.
     */
    @PostMapping("/updateWatchedStatus")
    public String updateWatchStatus(@RequestParam(defaultValue = "1", name = "page") Integer page, @RequestParam("title") String title,
                                    @RequestParam("year") String year, @RequestParam("status") String status, Principal principal, Model model) {
        CustomUser user = userService.findUserAndMovies(principal.getName());
        userService.updateMovieForUser(principal.getName(), title, year, status);
        return returnListFragment(user, page, model);
    }

    /**
     * clear-search endpoint acts as a way to close divs on the page. HTMX is used to inject the empty DIV inside
     * the DIV that they are currently in.
     *
     * @param model the model.
     * @return HTML fragment that consists of an empty div element.
     */
    @GetMapping("/clear-search")
    public String clearSearch(Model model) {
        return "/fragments/empty :: empty";
    }

    /**
     * Helper method to get pageination logic for users personal list page.
     *
     * @param model The model.
     * @param user  The current user.
     * @param page  The page that the user is on.
     * @param size  The amount of movies to be displayed per page, 10.
     */
    private void addPaginationAttributes(Model model, CustomUser user, int page, int size) {
        int totalPages = (int) Math.ceil((double) user.getMovies().size() / size);
        List<Movie> userMovies = userService.getPagedMovies(user, page, size);
        model.addAttribute("movies", userMovies);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", totalPages);
    }

    /**
     * Helper method used to bundle the pageination logic and return statement for the users list page.
     *
     * @param user  The current user.
     * @param page  The current page that the user is on.
     * @param model The model.
     * @return String value for the HTML fragment of the users personal movie list.
     */
    private String returnListFragment(CustomUser user, int page, Model model) {
        addPaginationAttributes(model, user, page, 10);
        return "/fragments/personal-list :: movie-list";
    }

}
