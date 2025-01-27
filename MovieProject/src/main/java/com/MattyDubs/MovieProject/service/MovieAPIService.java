package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Movie;

import java.util.List;

public interface MovieAPIService {

    Movie getMovieByTitleAndYear(String title, String year);

    Movie getMovieByTitle(String title);

}
