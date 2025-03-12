package com.MattyDubs.MovieProject.dao;

import com.MattyDubs.MovieProject.entity.Reply;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ReplyDAOImpl implements ReplyDAO {

    private final EntityManager entityManager;

    @Autowired
    public ReplyDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void saveReply(Reply reply) {
        entityManager.persist(reply);
    }

    @Override
    public List<Reply> findAll() {
        return entityManager.createQuery("SELECT r FROM Reply r", Reply.class).getResultList();
    }

    @Override
    @Transactional
    public void deleteReply(int id) {
        Reply replyToRemove = entityManager.find(Reply.class, id);
        entityManager.remove(replyToRemove);
    }

    @Override
    @Transactional
    public void updateReply(Reply reply) {
        entityManager.merge(reply);
    }

    @Override
    public Reply findById(int id) {
        return entityManager.find(Reply.class, id);
    }
}
