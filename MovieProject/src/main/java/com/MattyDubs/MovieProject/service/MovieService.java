package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Movie;

import java.util.List;

public interface MovieService {
    void save(Movie movie, CustomUser user);

    void deleteMovie(Movie movie);

    Movie findById(int id);

    List<Movie> findByTitleYear(String title, String year);

    List<Movie> findAll();

    List<Movie> findAllByUser(CustomUser user);

    List<Movie> findByTitleYearUser(String title, String year, CustomUser user);
}
