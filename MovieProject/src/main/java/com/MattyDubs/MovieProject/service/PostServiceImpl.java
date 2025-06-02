package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.dao.PostDAO;
import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Post;
import com.MattyDubs.MovieProject.security.NoPostFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostDAO postDAO;

    @Autowired
    public PostServiceImpl(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    @Override
    @Transactional
    public void update(Post post) {
        postDAO.update(post);
    }

    @Override
    @Transactional
    public Post findById(int id) {
        return postDAO.findById(id);
    }

    @Override
    public Post findByTitle(String title) {
        Post post = postDAO.findByTitle(title);
        if (post == null) {
            throw new NoPostFoundException("No post found with title: " + title);
        }
        return post;
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        postDAO.deleteById(id);
    }

    @Override
    @Transactional
    public void deletePost(Post post) {
        postDAO.deletePost(post);
    }

    @Override
    @Transactional
    public void savePost(Post post) {
        postDAO.savePost(post);
    }

    public List<Post> findAllContent() {
        return postDAO.findAllContent();
    }

    public Post findPostAndReplies(int id) {
        Post post = postDAO.findPostAndReplies(id);
        if (post == null) {
            throw new NoPostFoundException("No post found with id " + id);
        }
        return post;
    }

    @Override
    @Transactional
    public void savePostForPage(CustomUser user, Post post) {
        post.setUpvotes(0);
        post.setUser(user);
        savePost(post);
    }

    public Post findPostUserAndReplies(int id) {
        Post post = postDAO.findPostUserAndReplies(id);
        if (post == null) {
            throw new NoPostFoundException("No post found with id " + id);
        }
        return post;
    }

    @Override
    public List<Post> findUserPosts(int id) {
        return postDAO.findUserPosts(id);
    }
}
