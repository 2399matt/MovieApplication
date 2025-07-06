package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.dao.UserDAO;
import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.UserMovies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public CustomUser save(CustomUser user) {
        return userDAO.save(user);
    }

    @Override
    public CustomUser findByUsername(String username) {
        return userDAO.findByUsername(username);
    }


    @Override
    public boolean checkUserExists(String username) {
        return userDAO.checkUserExists(username);
    }

    public List<UserMovies> getPagedMovies(CustomUser user, int page, int size) {
        List<UserMovies> movies = user.getMovies();
        int start = (page - 1) * size;
        int end = Math.min(start + size, movies.size());
        return movies.subList(start, end);
    }

    @Override
    public CustomUser getUserAndMovies(CustomUser user) {
        return userDAO.getUserAndMovies(user);
    }
}
