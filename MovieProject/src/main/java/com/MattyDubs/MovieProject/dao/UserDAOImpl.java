package com.MattyDubs.MovieProject.dao;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.UserMovies;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO {

    private final EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public CustomUser save(CustomUser user) {
        return entityManager.merge(user);
    }

    @Override
    public CustomUser findByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM CustomUser u WHERE u.username=:username", CustomUser.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public CustomUser findByToken(String token) {
        try {
            return entityManager.createQuery("SELECT u FROM CustomUser u WHERE u.verificationToken = :token", CustomUser.class)
                    .setParameter("token", token)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public CustomUser findUserAndMovies(String username) {

        // JOIN FETCH used here to reduce the number of queries to the DB.
        // Will return the user and all of their associated movies in one query for the list endpoint.
        // WE COULD also do user.getMovies() and since the we're in a transaction, it would lazy load the movies. But this is more explicit, and would
        // still technically be two queries, so join fetch is safer.

        return entityManager.createQuery("SELECT u FROM CustomUser u LEFT JOIN FETCH u.movies WHERE u.username=:username", CustomUser.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public List<CustomUser> findAll() {
        return entityManager.createQuery("SELECT u FROM CustomUser u", CustomUser.class).getResultList();
    }

    @Override
    public boolean checkUserExists(String username) {
        long exists = entityManager.createQuery("SELECT COUNT(u) FROM CustomUser u WHERE u.username=:username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
        System.out.println(exists);
        return exists == 0;
    }

    @Override
    public CustomUser getUserAndMovies(CustomUser user) {
        List<UserMovies> movies = entityManager.createQuery("SELECT m FROM UserMovies m LEFT JOIN FETCH m.movie WHERE m.user.id = :id", UserMovies.class)
                .setParameter("id", user.getId())
                .getResultList();
        user.setMovies(movies);
        return user;
    }
}
