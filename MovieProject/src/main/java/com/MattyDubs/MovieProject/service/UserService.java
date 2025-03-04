package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.entity.CustomUser;

public interface UserService {

    CustomUser save(CustomUser user);

    CustomUser findByUsername(String username);

    CustomUser findUserAndMovies(String username);

    void updateMovieForUser(String username, String title, String year, String status);
}
