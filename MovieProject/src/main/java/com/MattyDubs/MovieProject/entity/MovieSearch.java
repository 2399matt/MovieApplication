package com.MattyDubs.MovieProject.entity;

import org.springframework.stereotype.Component;

@Component
public class MovieSearch {

    /**
     * MovieSearch class holds the data for the movie that the user is searching for.
     * Data will be acquired through input fields on the webpage and bound to this POJO.
     */
    private String title;
    private String year;

    public MovieSearch() {

    }

    public MovieSearch(String title, String year) {
        this.title = title;
        this.year = year;
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
}
