package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.entity.Movie;

public interface MovieAPIService {

    Movie getMovieByTitleAndYear(String title, String year);

    Movie getMovieByTitle(String title);
}
