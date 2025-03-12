package com.MattyDubs.MovieProject.dao;

import com.MattyDubs.MovieProject.entity.Reply;

import java.util.List;

/**
 * DAO interface for the Reply entity. Defines the method to be used on the replies DB table.
 */
public interface ReplyDAO {

    void saveReply(Reply reply);

    List<Reply> findAll();

    void deleteReply(int id);

    void updateReply(Reply reply);

    Reply findById(int id);
}
