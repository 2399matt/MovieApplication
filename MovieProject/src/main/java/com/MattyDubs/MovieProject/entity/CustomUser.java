package com.MattyDubs.MovieProject.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customusers")
public class CustomUser {

    /**
     * CustomUser class, used to hold users that are created with Spring Security.
     * Mapped with the movie class to allow users to have their own personal list of movies
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Movie> movies;

    public CustomUser() {

    }

    public CustomUser(String username) {
        this.username = username;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
