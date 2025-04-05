package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.dao.LikesDAOImpl;
import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Likes;
import com.MattyDubs.MovieProject.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikesServiceImpl implements LikesService {

    private final LikesDAOImpl likesDAOImpl;

    public LikesServiceImpl(LikesDAOImpl likesDAOImpl) {
        this.likesDAOImpl = likesDAOImpl;
    }

    public void saveLikes(Likes like, CustomUser user, Post post) {
        like.setUser(user);
        like.setPost(post);
        likesDAOImpl.saveLikes(like);
    }

    public List<Likes> findByUser(CustomUser user) {
        return likesDAOImpl.findByUser(user);
    }

    public List<Likes> findByPost(Post post) {
        return likesDAOImpl.findByPost(post);
    }

    public boolean findByUserAndPost(CustomUser user, Post post) {
        return likesDAOImpl.findByUserAndPost(user, post);
    }
}
