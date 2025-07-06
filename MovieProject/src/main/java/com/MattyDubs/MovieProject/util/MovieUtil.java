package com.MattyDubs.MovieProject.util;


import com.MattyDubs.MovieProject.data.TopMovieModel;
import com.MattyDubs.MovieProject.entity.Movie;
import org.springframework.stereotype.Component;

/**
 * Movie helper class used by the MovieController.
 */
@Component
public class MovieUtil {

    public MovieUtil() {

    }

    /**
     * copyMovie method is used to copy the fields of one movie to a new movie object. This is used for when we
     * check our tables for the existence of a movie. If it is found, we copy that movie over (except for the attached
     * id) to a new object, which will allow the user to save that movie.
     *
     * @param movie The movie object to be copied.
     * @return A new movie object with the same fields as the original movie.
     */
//    public Movie copyMovie(Movie movie) {
//        Movie newMovie = new Movie();
//        newMovie.setId(movie.getId());
//        newMovie.setYear(movie.getYear());
//        newMovie.setTitle(movie.getTitle());
//        newMovie.setActors(movie.getActors());
//        newMovie.setImdbId(movie.getImdbId());
//        newMovie.setDirector(movie.getDirector());
//        newMovie.setRated(movie.getRated());
//        newMovie.setScore(movie.getScore());
//        newMovie.setGenre(movie.getGenre());
//        newMovie.setPlot(movie.getPlot());
//        newMovie.setImageURL(movie.getImageURL());
//        return newMovie;
//    }

    /**
     * mapFromTopMovieModel method is used to map the fields of a top-movie to a new movie object. This is used for if
     * the movie exists in the top-movie list.
     *
     * @param tm The top-movie to be copied.
     * @return A new movie object with the same fields as the top-movie.
     */
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
