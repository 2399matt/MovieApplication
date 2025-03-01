package com.MattyDubs.MovieProject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


/**
 * Config class used for interacting with external API.
 * WebClient methods that are used to call the OMDB API for the movie information.
 * Builders for a list of movies by title/year, or a single movie by title/year.
 * Single movie call provides more information on the movie.
 */
@Configuration
public class MovieConfig {

    @Value("${movieURL}")
    private String movieURL;
    @Value("${movieKey}")
    private String movieKey;

    public MovieConfig() {

    }

    public WebClient.Builder webClientBuilder(String title, String year, String type) {
        return WebClient.builder().baseUrl(movieURL + movieKey + "&s=" + title + "&y=" + year + "&type=" + type);
    }

    public WebClient.Builder webClientBuilder(String title, String type) {
        return WebClient.builder().baseUrl(movieURL + movieKey + "&s=" + title + "&type=" + type);

    }

    public WebClient.Builder getSingleMovie(String title, String year) {
        return WebClient.builder().baseUrl(movieURL + movieKey + "&t=" + title + "&y=" + year);

    }
}
