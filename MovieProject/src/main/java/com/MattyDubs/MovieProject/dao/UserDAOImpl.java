package com.MattyDubs.MovieProject.dao;

import com.MattyDubs.MovieProject.entity.CustomUser;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDAOImpl implements UserDAO {

    /**
     * userDAO class for interacting with the DB. This is for CustomUsers, not for Spring-Security.
     * Basic DB methods for finding/saving the user.
     */
    private final EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public CustomUser save(CustomUser user) {
        return entityManager.merge(user);
    }

    @Override
    public CustomUser findByUsername(String username) {
        // TODO: Switch to optional.
        CustomUser user = entityManager.createQuery("SELECT u FROM CustomUser u WHERE u.username=:username", CustomUser.class)
                .setParameter("username", username)
                .getSingleResult();
        if (user == null)
            throw new RuntimeException("User not found.");
        return user;
    }

    public CustomUser findUserAndMovies(String username){

        // JOIN FETCH used here to reduce the number of queries to the DB.
        // Will return the user and all of their associated movies in one query for the list endpoint.
        // WE COULD also do user.getMovies() and since the we're in a transaction, it would lazy load the movies. But this is more explicit, and would
        // still technically be two queries, so join fetch is safer.

        return entityManager.createQuery("SELECT u FROM CustomUser u LEFT JOIN FETCH u.movies WHERE u.username=:username", CustomUser.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
