package com.epam.spring.movieTheaterManagement.service;

import com.epam.spring.movieTheaterManagement.dao.UserDao;
import com.epam.spring.movieTheaterManagement.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TheaterUserService implements UserService {
    private UserDao userDao;

    public TheaterUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public User save(@Nonnull User user) {
        return userDao.save(user);
    }

    @Override
    public void remove(@Nonnull User user) {
        userDao.remove(user);
    }

    @Override
    public User getById(@Nonnull Long id) {
        return userDao.getById(id);
    }

    @Nonnull
    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }
}
