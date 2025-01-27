package com.MattyDubs.MovieProject.dao;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.WebUser;

public interface UserDAO {
    CustomUser save(CustomUser user);

    CustomUser findByUsername(String username);
}
