package com.epam.spring.movieTheaterManagement.dao;

import com.epam.spring.movieTheaterManagement.domain.User;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class UserDao implements Dao<User> {
    private List<User> usersList = new ArrayList<>();

    public User createUser(Long id, String firstName, String lastName, String email, LocalDateTime birthday) {
        User newUser = new User(id, firstName, lastName, email, birthday);
        usersList.add(newUser);
        return newUser;
    }

    @Override
    public List<User> getAll() {
        return usersList;
    }

    public User save(@Nonnull User user) {
        usersList.add(user);
        return user;
    }

    public boolean remove(@Nonnull User user) {
        return usersList.remove(user);
    }

    public boolean removeById(@Nonnull Long id) {
        Predicate<User> userPredicate = p -> p.getId().equals(id);
        return usersList.removeIf(userPredicate);
    }

    public User getById(@Nonnull Long id) {
        return usersList
                .stream()
                .filter(user -> user.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public User getUserByEmail(@Nonnull String email) {
        return usersList
                .stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findAny()
                .orElse(null);
    }
}
