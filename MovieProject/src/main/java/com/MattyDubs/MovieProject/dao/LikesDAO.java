package com.MattyDubs.MovieProject.dao;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Likes;
import com.MattyDubs.MovieProject.entity.Post;

import java.util.List;

public interface LikesDAO {

    void saveLikes(Likes like);

    List<Likes> findByUser(CustomUser user);

    List<Likes> findByPost(Post post);

    boolean findByUserAndPost(CustomUser user, Post post);
}
