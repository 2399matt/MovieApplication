package com.MattyDubs.MovieProject.dao;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Movie;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class MovieDAOImpl implements MovieDAO {

    /**
     * MovieDAO class used to interact with the movie table in our DB.
     * basic methods for saving, removing, and finding movies based on different needs.
     */
    private final EntityManager entityManager;

    @Autowired
    public MovieDAOImpl(EntityManager entityManager, UserDAO userDAO) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Movie movie, CustomUser user) {
        List<Movie> movies = findByTitleYearUser(movie.getTitle(), movie.getYear(), user);
        if (movies.isEmpty())
            entityManager.persist(movie);
    }

    @Override
    @Transactional
    public void deleteMovie(Movie movie) {
        entityManager.remove(movie);
    }

    @Override
    public Movie findById(int id) {
        Movie movie = entityManager.find(Movie.class, id);
        return movie;
    }

    @Override
    public List<Movie> findByTitleYear(String title, String year) {
        return entityManager.createQuery("SELECT m FROM Movie m WHERE m.title=:title AND m.year=:year", Movie.class)
                .setParameter("title", title)
                .setParameter("year", year)
                .getResultList();
    }

    public List<Movie> findAll() {
        return entityManager.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
    }

    public List<Movie> findAllByUser(CustomUser user) {
        return entityManager.createQuery("SELECT m FROM Movie m WHERE m.user.id=:id", Movie.class)
                .setParameter("id", user.getId())
                .getResultList();

    }

    public List<Movie> findByTitleYearUser(String title, String year, CustomUser user) {
        return entityManager.createQuery("SELECT m FROM Movie m WHERE m.title = :title AND m.year=:year AND m.user.id = :id", Movie.class)
                .setParameter("title", title)
                .setParameter("year", year)
                .setParameter("id", user.getId())
                .getResultList();

    }


}
