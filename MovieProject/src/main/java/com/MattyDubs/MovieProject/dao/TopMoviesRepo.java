package com.MattyDubs.MovieProject.dao;

import com.MattyDubs.MovieProject.data.TopMovieModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * TopMoviesRepo uses the JPARepository to interact with the top-movies table. NOTE: the top-movies have
 * NO relationship to customUsers.
 */
public interface TopMoviesRepo extends JpaRepository<TopMovieModel, Integer> {

    TopMovieModel findByTitle(String title);

    List<TopMovieModel> findAllByGenre(String genre);
}
