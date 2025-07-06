package com.MattyDubs.MovieProject.event;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.UserMovies;

import java.util.List;

public class MovieEmailRequestEvent {

    private final List<UserMovies> movies;
    private final CustomUser user;

    public MovieEmailRequestEvent(List<UserMovies> movies, CustomUser user) {
        this.movies = movies;
        this.user = user;
    }

    public CustomUser getUser() {
        return user;
    }

    public List<UserMovies> getMovies() {
        return movies;
    }

}
