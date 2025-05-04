package com.MattyDubs.MovieProject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


/**
 * Config class used for interacting with external API.
 * WebClient methods that are used to call the OMDB API for the movie information.
 * Builders for a list of movies by title/year, or a single movie by title/year.
 * Single movie call provides more information on the movie.
 */
@Component
public class MovieConfig {

    @Value("${movieURL}")
    private String movieURL;
    @Value("${movieKey}")
    private String movieKey;

    public MovieConfig() {

    }

    public RestClient.Builder getMovieWithYear(String title, String year, String type) {
        return RestClient.builder().baseUrl(movieURL + movieKey + "&s=" + title + "&y=" + year + "&type=" + type);
    }

    public RestClient.Builder getMovieWithoutYear(String title, String type) {
        return RestClient.builder().baseUrl(movieURL + movieKey + "&s=" + title + "&type=" + type);
    }

    public RestClient.Builder getSingleMovie(String title, String year) {
        return RestClient.builder().baseUrl(movieURL + movieKey + "&t=" + title + "&y=" + year);
    }

}
