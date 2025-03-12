package com.MattyDubs.MovieProject.dao;

import com.MattyDubs.MovieProject.entity.Post;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PostDAOImpl implements PostDAO {

    private final EntityManager entityManager;

    @Autowired
    public PostDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void update(Post post) {
        entityManager.merge(post);
    }

    @Override
    @Transactional
    public Post findById(int id) {
        return entityManager.find(Post.class, id);
    }

    @Override
    public Post findByTitle(String title) {
        return entityManager.createQuery("SELECT p FROM Post p WHERE p.title = :title", Post.class)
                .setParameter("title", title)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Post postToRemove = entityManager.find(Post.class, id);
        entityManager.remove(postToRemove);
    }

    @Override
    @Transactional
    public void deletePost(Post post) {
        entityManager.remove(post);
    }

    @Override
    @Transactional
    public void savePost(Post post) {
        entityManager.persist(post);
    }

    @Override
    public List<Post> findAllContent() {
        return entityManager.createQuery("SELECT p FROM Post p LEFT JOIN FETCH p.replies", Post.class)
                .getResultList();
    }

    public Post findPostAndReplies(int id) {
        return entityManager.createQuery("SELECT p FROM Post p LEFT JOIN FETCH p.replies WHERE p.id = :id", Post.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
