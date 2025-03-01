package com.MattyDubs.MovieProject.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Objects;


/**
 * Movie entity class used to interact with the movie table in the DB.
 * Contains a CustomUser field for the many-to-one relationship.
 * Class is also used for the API call to map the JSON info to our POJO.
 */
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JsonProperty("Title")
    @Column(name = "title")
    private String title;

    @JsonProperty("Year")
    @Column(name = "year")
    private String year;

    @JsonProperty("Rated")
    @Column(name = "rated")
    private String rated;

    @JsonProperty("Genre")
    @Column(name = "genre")
    private String genre;

    @JsonProperty("Actors")
    @Column(name = "actors")
    private String actors;

    @JsonProperty("Plot")
    @Column(name = "plot")
    private String plot;

    @JsonProperty("imdbRating")
    @Column(name = "score")
    private String score;

    @JsonProperty("Poster")
    @Column(name = "imageurl")
    private String imageURL;

    @JsonProperty("imdbID")
    @Column(name = "imdbid")
    private String imdbId;

    @JsonProperty("Director")
    @Column(name = "director")
    private String director;

    @Column(name = "watched", columnDefinition = "boolean default false")
    private boolean watched;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomUser user;

    @JsonProperty("Type")
    @Transient
    private String type;


    public Movie() {

    }

    public Movie(String title, String year, String rated, String genre, String actors, String plot,
                 String score, String imageURL, String imdbId, String director, String imdbURL, String type) {
        this.title = title;
        this.year = year;
        this.rated = rated;
        this.genre = genre;
        this.actors = actors;
        this.plot = plot;
        this.score = score;
        this.imageURL = imageURL;
        this.imdbId = imdbId;
        this.director = director;
    }

    public boolean getWatched() {
        return this.watched;
    }

    public void setWatched(Boolean bool) {
        this.watched = bool;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public String getImdbURL() {
        String imdbURL = "https://www.imdb.com/title/";
        return imdbURL;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", rated='" + rated + '\'' +
                ", genre='" + genre + '\'' +
                ", actors='" + actors + '\'' +
                ", plot='" + plot + '\'' +
                ", score='" + score + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id && Objects.equals(title, movie.title) && Objects.equals(year, movie.year) && Objects.equals(rated, movie.rated) && Objects.equals(genre, movie.genre) && Objects.equals(actors, movie.actors) && Objects.equals(plot, movie.plot) && Objects.equals(score, movie.score) && Objects.equals(imageURL, movie.imageURL) && Objects.equals(imdbId, movie.imdbId) && Objects.equals(director, movie.director) && Objects.equals(user, movie.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, year, rated, genre, actors, plot, score, imageURL, imdbId, director, user);
    }

}
