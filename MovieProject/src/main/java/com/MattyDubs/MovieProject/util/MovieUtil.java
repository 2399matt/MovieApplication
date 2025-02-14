package com.MattyDubs.MovieProject.util;


import com.MattyDubs.MovieProject.data.TopMovieModel;
import com.MattyDubs.MovieProject.entity.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieUtil {

    public MovieUtil() {

    }

    public Movie copyMovie(Movie movie) {
        Movie newMovie = new Movie();
        newMovie.setUser(movie.getUser());
        newMovie.setYear(movie.getYear());
        newMovie.setTitle(movie.getTitle());
        newMovie.setActors(movie.getActors());
        newMovie.setImdbId(movie.getImdbId());
        newMovie.setDirector(movie.getDirector());
        newMovie.setRated(movie.getRated());
        newMovie.setScore(movie.getScore());
        newMovie.setGenre(movie.getGenre());
        newMovie.setPlot(movie.getPlot());
        newMovie.setImageURL(movie.getImageURL());
        return newMovie;
    }

    public Movie mapFromTopMovieModel(TopMovieModel tm) {
        Movie movie = new Movie();
        movie.setYear(tm.getYear());
        movie.setTitle(tm.getTitle());
        movie.setActors(tm.getActors());
        movie.setImdbId(tm.getImdbId());
        movie.setDirector(tm.getDirector());
        movie.setRated(tm.getRated());
        movie.setScore(tm.getScore());
        movie.setGenre(tm.getGenre());
        movie.setPlot(tm.getPlot());
        movie.setImageURL(tm.getImageURL());
        return movie;
    }
}
