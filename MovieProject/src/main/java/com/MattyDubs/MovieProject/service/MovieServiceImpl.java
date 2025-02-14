package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.dao.MovieDAO;
import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    /**
     * Service class that uses a movieDAO object to interact with the database.
     * Provides an extra layer between our controller and the DB.
     */
    private final MovieDAO movieDAO;

    @Autowired
    public MovieServiceImpl(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    @Transactional
    public void save(Movie movie, CustomUser user) {
         movieDAO.save(movie, user);
    }

    @Override
    @Transactional
    public void deleteMovie(Movie movie) {
        movieDAO.deleteMovie(movie);
    }

    @Override
    public Movie findById(int id) {
        return movieDAO.findById(id);
    }

    @Override
    public List<Movie> findByTitleYear(String title, String year) {
        return movieDAO.findByTitleYear(title, year);
    }

    @Override
    public List<Movie> findAll() {
        return movieDAO.findAll();
    }

    @Override
    public List<Movie> findAllByUser(CustomUser user) {
        return movieDAO.findAllByUser(user);
    }

    @Override
    public List<Movie> findByTitleYearUser(String title, String year, CustomUser user){
        return movieDAO.findByTitleYearUser(title, year, user);
    }

    @Override
    public Movie singleFindByTitleYear(String title, String year){
        return movieDAO.singleFindByTitleYear(title, year);
    }
}
