package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.dao.ReplyDAO;
import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Post;
import com.MattyDubs.MovieProject.entity.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyDAO replyDAO;

    @Autowired
    public ReplyServiceImpl(ReplyDAO replyDAO) {
        this.replyDAO = replyDAO;
    }

    @Override
    @Transactional
    public void saveReply(Reply reply) {
        replyDAO.saveReply(reply);
    }

    @Override
    public List<Reply> findAll() {
        return replyDAO.findAll();
    }

    @Override
    @Transactional
    public void deleteReply(int id) {
        replyDAO.deleteReply(id);
    }

    @Override
    @Transactional
    public void updateReply(Reply reply) {
        replyDAO.updateReply(reply);
    }

    @Override
    public Reply findById(int id) {
        return replyDAO.findById(id);
    }

    @Override
    @jakarta.transaction.Transactional
    public void saveReplyForPage(Reply reply, CustomUser user, Post post) {
        reply.setPost(post);
        reply.setUser(user);
        post.addReply(reply);
        user.addReply(reply);
        saveReply(reply);
    }
}
