package com.MattyDubs.MovieProject.dao;

import com.MattyDubs.MovieProject.entity.Post;

import java.util.List;

/**
 * PostDAO, used to interact with the posts DB table and preform basic methods.
 */
public interface PostDAO {

    void update(Post post);

    Post findById(int id);

    Post findByTitle(String title);

    void deleteById(int id);

    void deletePost(Post post);

    void savePost(Post post);

    List<Post> findAllContent();

    Post findPostAndReplies(int id);

    Post findPostUserAndReplies(int id);
}
