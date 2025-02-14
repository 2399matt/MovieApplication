package com.MattyDubs.MovieProject.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name="top_movies")
public class TopMovieModel{

    // Separate movie entity for the top 250 movies table.
    // This is to avoid conflicts with the movie entity used for the user's saved movies.

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




    public TopMovieModel() {

    }

    public TopMovieModel(String title, String year, String rated, String genre, String actors, String plot,
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
        TopMovieModel movie = (TopMovieModel) o;
        return id == movie.id && Objects.equals(title, movie.title) && Objects.equals(year, movie.year) && Objects.equals(rated, movie.rated) && Objects.equals(genre, movie.genre) && Objects.equals(actors, movie.actors) && Objects.equals(plot, movie.plot) && Objects.equals(score, movie.score) && Objects.equals(imageURL, movie.imageURL) && Objects.equals(imdbId, movie.imdbId) && Objects.equals(director, movie.director);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, year, rated, genre, actors, plot, score, imageURL, imdbId, director);
    }

}
