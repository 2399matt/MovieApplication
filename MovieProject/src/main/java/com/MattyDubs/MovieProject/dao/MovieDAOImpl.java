package com.MattyDubs.MovieProject.dao;

import com.MattyDubs.MovieProject.entity.Movie;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class MovieDAOImpl implements MovieDAO {

    private final EntityManager entityManager;

    @Autowired
    public MovieDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Movie save(Movie movie) {
        return entityManager.merge(movie);
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
        return entityManager.createQuery("SELECT m FROM Movie m WHERE m.title=:title AND m.year=:year")
                .setParameter("title", title)
                .setParameter("year", year)
                .getResultList();
    }

    public List<Movie> findAll() {
        return entityManager.createQuery("SELECT m FROM Movie m").getResultList();
    }


}
