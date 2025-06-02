package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.dao.UserDAO;
import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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
    @Transactional
    public void updateMovieForUser(CustomUser user, String title, String year, String status) {
        //TODO Make use of OPTIONAL here? Maybe ifPresent
        //TODO Again, we'll have to fetch with... user.setMovies(movieService.findAllByUser(user));
        //user.setMovies(movieService.findAllByUser(user));
        Movie movieToUpdate = user.getMovies()
                //user.getMovies()
                .stream()
                .filter(mov -> mov.getTitle().equals(title) && mov.getYear().equals(year))
                .findFirst()
//              .ifPresent(movie->{
//                    movie.setWatched(Boolean.valueOf(status));
//                    movieService.updateMovie(movie);
//                });
                .orElse(null);
        if (movieToUpdate != null) {
            movieToUpdate.setWatched(Boolean.valueOf(status));
            movieService.updateMovie(movieToUpdate);
        }
    }

    @Override
    public boolean checkUserExists(String username) {
        return userDAO.checkUserExists(username);
    }

    public List<Movie> getPagedMovies(CustomUser user, int page, int size) {
        //TODO This is what we'll have to do. Can't lazy load with userdetails fetch... user.setMovies(movieService.findAllByUser(user));
        //user.setMovies(movieService.findAllByUser(user));
        List<Movie> movies = user.getMovies();
        int start = (page - 1) * size;
        int end = Math.min(start + size, movies.size());
        return movies.subList(start, end);
    }

    @Override
    public CustomUser getUserAndMovies(CustomUser user) {
        return userDAO.getUserAndMovies(user);
    }
}
