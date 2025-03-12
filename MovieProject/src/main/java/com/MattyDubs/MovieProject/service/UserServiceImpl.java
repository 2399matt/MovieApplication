package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.dao.UserDAO;
import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

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

    @Override
    public boolean checkUserExists(String username) {
        List<CustomUser> users = userDAO.findAll();
        CustomUser userCheck = users
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        return userCheck != null;
    }

    public List<Movie> getPagedMovies(CustomUser user, int page, int size) {
        List<Movie> movies = user.getMovies();
        int start = (page - 1) * size;
        int end = Math.min(start + size, movies.size());
        return movies.subList(start, end);
    }
}
