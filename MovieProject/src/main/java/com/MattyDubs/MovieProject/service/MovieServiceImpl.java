package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.dao.MovieDAO;
import com.MattyDubs.MovieProject.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieDAO movieDAO;

    @Autowired
    public MovieServiceImpl(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    public Movie save(Movie movie) {
        return movieDAO.save(movie);
    }

    @Override
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
}
