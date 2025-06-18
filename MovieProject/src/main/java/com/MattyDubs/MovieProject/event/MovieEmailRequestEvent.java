package com.MattyDubs.MovieProject.event;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Movie;

import java.util.List;

public class MovieEmailRequestEvent {

    private final List<Movie> movies;
    private final CustomUser user;

    public MovieEmailRequestEvent(List<Movie> movies, CustomUser user) {
        this.movies = movies;
        this.user = user;
    }

    public CustomUser getUser() {
        return user;
    }

    public List<Movie> getMovies() {
        return movies;
    }

}
