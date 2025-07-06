package com.MattyDubs.MovieProject.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "usermovies")
public class UserMovies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomUser user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(name = "watched", columnDefinition = "boolean default false")
    private boolean watched;

    @Column(name = "userrating")
    private int userRating;

    public UserMovies() {

    }

    public UserMovies(CustomUser user, Movie movie) {
        this.user = user;
        this.movie = movie;
        this.watched = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public boolean getWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

}
