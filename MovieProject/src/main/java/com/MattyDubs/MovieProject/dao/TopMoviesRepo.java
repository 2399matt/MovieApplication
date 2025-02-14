package com.MattyDubs.MovieProject.dao;

import com.MattyDubs.MovieProject.data.TopMovieModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopMoviesRepo extends JpaRepository<TopMovieModel, Integer> {

    TopMovieModel findByTitle(String title);

    List<TopMovieModel> findAllByGenre(String genre);
}
