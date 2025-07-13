package com.MattyDubs.MovieProject.event;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.UserMovies;

import java.util.List;

public record MovieEmailRequestEvent(List<UserMovies> movies, CustomUser user) {

}
