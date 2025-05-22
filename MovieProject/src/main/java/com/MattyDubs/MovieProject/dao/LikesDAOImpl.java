package com.MattyDubs.MovieProject.dao;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Likes;
import com.MattyDubs.MovieProject.entity.Post;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class LikesDAOImpl implements LikesDAO {

    private final EntityManager entityManager;

    @Autowired
    public LikesDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void saveLikes(Likes like) {
        entityManager.persist(like);
    }

    public List<Likes> findByUser(CustomUser user) {
        return entityManager.createQuery("SELECT l FROM Likes l WHERE l.user.id=:id", Likes.class)
                .setParameter("id", user.getId())
                .getResultList();
    }

    public List<Likes> findByPost(Post post) {
        return entityManager.createQuery("SELECT l FROM Likes l WHERE l.post.id=:id", Likes.class)
                .setParameter("id", post.getId())
                .getResultList();
    }

    public boolean findByUserAndPost(CustomUser user, Post post) {
        Long exists = entityManager.createQuery("SELECT COUNT(l) FROM Likes l WHERE l.post.id=:id AND l.user.id=:uid", Long.class)
                .setParameter("id", post.getId())
                .setParameter("uid", user.getId())
                .getSingleResult();
        System.out.println(exists);
        return exists == 0;
    }
}
