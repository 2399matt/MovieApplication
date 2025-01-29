package com.MattyDubs.MovieProject.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MovieListContainer {

    @JsonProperty("Search")
    private List<Movie> movies;

    public MovieListContainer() {

    }


    public MovieListContainer(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
