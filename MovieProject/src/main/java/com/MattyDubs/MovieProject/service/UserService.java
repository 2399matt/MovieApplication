package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.entity.CustomUser;

public interface UserService {

    CustomUser save(CustomUser user);

    CustomUser findByUsername(String username);
}
