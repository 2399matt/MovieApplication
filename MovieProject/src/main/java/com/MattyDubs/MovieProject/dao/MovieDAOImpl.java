package com.MattyDubs.MovieProject.dao;

import com.MattyDubs.MovieProject.entity.Movie;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class MovieDAOImpl implements MovieDAO {

    private final EntityManager entityManager;

    @Autowired
    public MovieDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Movie save(Movie movie) {
        return entityManager.merge(movie);
    }

    @Override
    public void deleteMovie(Movie movie) {
        entityManager.remove(movie);
    }

    @Override
    public Movie findById(int id) {
        Movie movie = entityManager.find(Movie.class, id);
        return movie;
    }

    @Override
    public void updateMovie(Movie movie) {
        entityManager.merge(movie);
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


    public Movie singleFindByTitleYear(String title, String year) {
        return entityManager.createQuery("SELECT m FROM Movie m WHERE m.title = :title AND m.year=:year", Movie.class)
                .setParameter("title", title)
                .setParameter("year", year)
                .getResultList().stream().findFirst().orElse(null);
    }


    public long beforeSaveCheck(String title) {
        return entityManager.createQuery("SELECT COUNT(m) FROM Movie m WHERE m.title=:title", Long.class)
                .setParameter("title", title)
                .getSingleResult();
    }

}
