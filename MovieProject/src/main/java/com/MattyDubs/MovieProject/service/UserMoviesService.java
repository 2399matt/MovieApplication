package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.dao.UserMoviesDAO;
import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Movie;
import com.MattyDubs.MovieProject.entity.UserMovies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserMoviesService {

    private final UserMoviesDAO userMoviesDAO;

    @Autowired
    public UserMoviesService(UserMoviesDAO userMoviesDAO) {
        this.userMoviesDAO = userMoviesDAO;
    }

    public UserMovies findById(int id) {
        return userMoviesDAO.findById(id);
    }

    @Transactional
    public void saveMovieForUser(Movie movie, CustomUser user) {
        if (checkForDuplicateSave(user, movie.getTitle())) {
            UserMovies userMovies = new UserMovies();
            userMovies.setUser(user);
            userMovies.setMovie(movie);
            userMoviesDAO.saveMovieForUser(userMovies);
        }
    }

    @Transactional
    public void deleteMovieForUser(UserMovies userMovies) {
        userMovies.setUser(null);
        userMovies.setMovie(null);
        userMoviesDAO.deleteMovieForUser(userMovies);
    }

    @Transactional
    public void updateWatchedStatus(UserMovies userMovies, String status) {
        boolean watchedStatus = Boolean.parseBoolean(status);
        userMovies.setWatched(watchedStatus);
        userMoviesDAO.updateUserMovie(userMovies);
    }

    public List<UserMovies> findUserMovies(CustomUser user) {
        return userMoviesDAO.findUserMovies(user);
    }

    public boolean checkForDuplicateSave(CustomUser user, String title) {
        long exists = userMoviesDAO.checkForDuplicateSave(user, title);
        return exists == 0;
    }

    public UserMovies findByUserAndTitle(CustomUser user, String title) {
        return userMoviesDAO.findByUserAndTitle(user, title);
    }

    @Transactional
    public void updateScore(int id, String score) {
        UserMovies movieToUpdate = findById(id);
        movieToUpdate.setUserRating(Integer.parseInt(score));
        userMoviesDAO.updateUserMovie(movieToUpdate);
    }
}
