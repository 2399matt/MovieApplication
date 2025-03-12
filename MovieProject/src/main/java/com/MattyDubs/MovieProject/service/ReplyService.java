package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Post;
import com.MattyDubs.MovieProject.entity.Reply;

import java.util.List;

/**
 * Service class for the Reply entity.
 */
public interface ReplyService {

    void saveReply(Reply reply);

    List<Reply> findAll();

    void deleteReply(int id);

    void updateReply(Reply reply);

    Reply findById(int id);

    /**
     * Method for saving a reply and binding it to its user and post.
     *
     * @param reply The reply to be saved.
     * @param user  The user who made the reply.
     * @param post  The post the reply belongs to.
     */
    void saveReplyForPage(Reply reply, CustomUser user, Post post);
}
