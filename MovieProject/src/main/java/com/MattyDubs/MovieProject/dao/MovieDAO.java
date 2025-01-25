package com.MattyDubs.MovieProject.dao;


import com.MattyDubs.MovieProject.entity.Movie;

import java.util.List;

public interface MovieDAO {

    Movie save(Movie movie);

    void deleteMovie(Movie movie);

    Movie findById(int id);

    List<Movie> findByTitleYear(String title, String year);

    List<Movie> findAll();

}
