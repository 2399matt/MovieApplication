package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.dao.UserDAO;
import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserService class uses the UserDAO implementation to interact with the database. NOTE: this class is for our
 * CustomUser entity, not for Spring-Security.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * UserService class that uses a userDAO object to interact with the DB
     * THIS IS ONLY FOR CustomUsers!! Not related to Spring-Security.
     */
    private final UserDAO userDAO;
    private final MovieService movieService;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, MovieService movieService) {
        this.userDAO = userDAO;
        this.movieService = movieService;
    }

    @Override
    public CustomUser save(CustomUser user) {
        return userDAO.save(user);
    }

    @Override
    public CustomUser findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public CustomUser findUserAndMovies(String username) {
        return userDAO.findUserAndMovies(username);
    }

    /**
     * updateMovieForUser method is used to update the watched status of a movie for a given user. Works with the
     * update methods in the MovieController class.
     *
     * @param username Username for the current logged-in user.
     * @param title    Title of the movie to update.
     * @param year     Year that the movie was released.
     * @param status   Watched or unwatched status of the movie, string holds value of either true or false.
     */
    @Override
    public void updateMovieForUser(String username, String title, String year, String status) {
        CustomUser user = findUserAndMovies(username);
        Movie movieToUpdate = user.getMovies()
                .stream()
                .filter(mov -> mov.getTitle().equals(title) && mov.getYear().equals(year))
                .findFirst()
                .orElse(null);
        if (movieToUpdate != null) {
            movieToUpdate.setWatched(Boolean.valueOf(status));
            movieService.updateMovie(movieToUpdate);
        }
    }
}
