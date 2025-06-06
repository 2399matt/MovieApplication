package com.MattyDubs.MovieProject.dao;

import com.MattyDubs.MovieProject.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostDAOImpl implements PostDAO {

    private final EntityManager entityManager;

    @Autowired
    public PostDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void update(Post post) {
        entityManager.merge(post);
    }

    @Override
    public Post findById(int id) {
        return entityManager.find(Post.class, id);
    }

    @Override
    public Post findByTitle(String title) {
        try {
            return entityManager.createQuery("SELECT p FROM Post p WHERE p.title = :title", Post.class)
                    .setParameter("title", title)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Post findPostUserAndReplies(int id) {
        try {
            return entityManager.createQuery("SELECT p FROM Post p LEFT JOIN FETCH p.replies r LEFT JOIN FETCH r.user JOIN FETCH p.user WHERE p.id=:id", Post.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void deleteById(int id) {
        Post postToRemove = entityManager.find(Post.class, id);
        entityManager.remove(postToRemove);
    }

    @Override
    public void deletePost(Post post) {
        entityManager.remove(post);
    }

    @Override
    public void savePost(Post post) {
        entityManager.persist(post);
    }

    @Override
    public List<Post> findAllContent() {
        return entityManager.createQuery("SELECT p FROM Post p LEFT JOIN FETCH p.replies", Post.class)
                .getResultList();
    }

    public Post findPostAndReplies(int id) {
        try {
            return entityManager.createQuery("SELECT p FROM Post p LEFT JOIN FETCH p.replies WHERE p.id = :id", Post.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Post> findUserPosts(int id) {
        return entityManager.createQuery("SELECT p FROM Post p WHERE p.user.id = :id", Post.class)
                .setParameter("id", id)
                .getResultList();
    }
}
