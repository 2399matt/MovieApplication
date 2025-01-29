package com.MattyDubs.MovieProject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Configuration
public class MovieConfig {

    @Value("${movieURL}")
    private String movieURL;
    @Value("${movieKey}")
    private String movieKey;

    public MovieConfig() {

    }

    /**
     * WebClient that is used to call the OMDB API for the movie information.
     * @param title title of the movie
     * @param year year that the movie was released
     * @return The ready-to-build WebClient that houses the URL for the API call
     */
    public WebClient.Builder webClientBuilder(String title, String year) {
        return WebClient.builder().baseUrl(movieURL + movieKey + "&s=" + title + "&y=" + year);
    }

    public WebClient.Builder webClientBuilder(String title) {
        return WebClient.builder().baseUrl(movieURL + movieKey + "&s=" + title);

    }

    public WebClient.Builder getSingleMovie(String title, String year){
        return WebClient.builder().baseUrl(movieURL + movieKey + "&t=" + title + "&y=" + year);

    }
}
