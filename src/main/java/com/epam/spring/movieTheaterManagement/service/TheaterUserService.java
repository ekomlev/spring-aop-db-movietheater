package com.epam.spring.movieTheaterManagement.service;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.spring.movieTheaterManagement.dao.UserDao;
import com.epam.spring.movieTheaterManagement.domain.User;

@Service("theaterUserService")
public class TheaterUserService implements UserService {
    private UserDao userDao;

    @Autowired
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
