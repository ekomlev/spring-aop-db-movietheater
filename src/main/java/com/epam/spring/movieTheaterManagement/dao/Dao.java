package com.epam.spring.movieTheaterManagement.dao;

import java.util.Collection;

public interface Dao<T> {

    Collection<T> getAll();
}
