package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.dao.MovieDAO;
import com.MattyDubs.MovieProject.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class MovieServiceImpl implements MovieService {

    private final MovieDAO movieDAO;

    @Autowired
    public MovieServiceImpl(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    @Transactional
    public Movie save(Movie movie) {
        return movieDAO.save(movie);
    }

    @Override
    @Transactional
    public void deleteMovie(int id) {
        Movie movieCheck = findById(id);
        if (movieCheck != null)
            movieDAO.deleteMovie(movieCheck);
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
    public Movie singleFindByTitleYear(String title, String year) {
        return movieDAO.singleFindByTitleYear(title, year);
    }


    @Override
    @Transactional
    public void updateMovie(Movie movie) {
        movieDAO.updateMovie(movie);
    }

}
