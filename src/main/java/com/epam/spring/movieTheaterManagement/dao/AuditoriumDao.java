package com.epam.spring.movieTheaterManagement.dao;

import com.epam.spring.movieTheaterManagement.domain.Auditorium;

import java.util.Set;

public class AuditoriumDao implements Dao<Auditorium> {
    private static Set<Auditorium> auditoriumSet;

    public AuditoriumDao(Set<Auditorium> auditoriumSet) {
        AuditoriumDao.auditoriumSet = auditoriumSet;
    }

    @Override
    public Set<Auditorium> getAll() {
        return auditoriumSet;
    }

    public static Auditorium getByName(String name) {
        return auditoriumSet
                .stream()
                .filter(auditorium -> name.equals(auditorium.getName()))
                .findAny()
                .orElse(null);
    }
}
