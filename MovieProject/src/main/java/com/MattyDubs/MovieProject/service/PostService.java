package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Post;

import java.util.List;

/**
 * Service class for the Post entity.
 */
public interface PostService {

    void update(Post post);

    Post findById(int id);

    Post findByTitle(String title);

    void deleteById(int id);

    void deletePost(Post post);

    void savePost(Post post);

    /**
     * Query for finding all the posts and its corresponding replies.
     *
     * @return A list of posts and its replies.
     */
    List<Post> findAllContent();

    /**
     * Query for finding a single post and its replies.
     *
     * @param id id of the post to find.
     * @return The post and its replies.
     */
    Post findPostAndReplies(int id);

    /**
     * Method for saving a post and binding it to its proper user.
     *
     * @param user The user who made the post.
     * @param post The post to be saved.
     */
    void savePostForPage(CustomUser user, Post post);
}
