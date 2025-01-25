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

    public WebClient.Builder webClientBuilder(String title, String year) {
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
        System.out.println(movieURL + movieKey + "&t=" + encodedTitle + "&y=" + year);
        return WebClient.builder().baseUrl(movieURL + movieKey + "&t=" + title + "&y=" + year);
    }

    public WebClient.Builder webClientBuilder(String title) {
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
        return WebClient.builder().baseUrl(movieURL + movieKey + "&t=" + title);

    }
}
