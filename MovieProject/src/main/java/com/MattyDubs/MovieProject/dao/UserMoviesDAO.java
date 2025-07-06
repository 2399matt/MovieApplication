package com.MattyDubs.MovieProject.dao;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.UserMovies;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserMoviesDAO {

    private final EntityManager entityManager;

    @Autowired
    public UserMoviesDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UserMovies findById(int id) {
        return entityManager.find(UserMovies.class, id);
    }

    public void saveMovieForUser(UserMovies userMovies) {
        entityManager.persist(userMovies);
    }

    public void deleteMovieForUser(UserMovies userMovies) {
        entityManager.remove(userMovies);
    }

    public void updateUserMovie(UserMovies userMovies) {
        entityManager.merge(userMovies);
    }

    public List<UserMovies> findUserMovies(CustomUser user) {
        return entityManager.createQuery("SELECT um FROM UserMovies um LEFT JOIN FETCH um.movie WHERE um.user.id=:id", UserMovies.class)
                .setParameter("id", user.getId())
                .getResultList();
    }

    public long checkForDuplicateSave(CustomUser user, String title) {
        return entityManager.createQuery("SELECT COUNT(um) FROM UserMovies um WHERE um.user.id=:id AND um.movie.title=:title", Long.class)
                .setParameter("id", user.getId())
                .setParameter("title", title)
                .getSingleResult();
    }

    public UserMovies findByUserAndTitle(CustomUser user, String title) {
        try {
            return entityManager.createQuery("SELECT um FROM UserMovies um WHERE um.movie.title=:title AND um.user.id=:id", UserMovies.class)
                    .setParameter("title", title)
                    .setParameter("id", user.getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new RuntimeException("No UM found with title " + title + " and id " + user.getId());
        }
    }
}
