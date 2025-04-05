package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Likes;
import com.MattyDubs.MovieProject.entity.Post;

import java.util.List;

public interface LikesService {

    void saveLikes(Likes like, CustomUser user, Post post);

    List<Likes> findByUser(CustomUser user);

    List<Likes> findByPost(Post post);

    boolean findByUserAndPost(CustomUser user, Post post);
}
