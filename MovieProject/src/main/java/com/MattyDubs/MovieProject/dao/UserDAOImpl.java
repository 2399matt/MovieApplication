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
        CustomUser user = entityManager.createQuery("SELECT u FROM CustomUser u WHERE u.username=:username", CustomUser.class)
                .setParameter("username", username)
                .getSingleResult();
        if (user == null)
            throw new RuntimeException("User not found.");
        return user;
    }
}
