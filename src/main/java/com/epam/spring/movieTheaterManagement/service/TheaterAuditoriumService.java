package com.epam.spring.movieTheaterManagement.service;

import com.epam.spring.movieTheaterManagement.dao.AuditoriumDao;
import com.epam.spring.movieTheaterManagement.domain.Auditorium;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class TheaterAuditoriumService implements AuditoriumService {
    private AuditoriumDao auditoriumDao;

    public TheaterAuditoriumService(Set<Auditorium> auditoriumSet) {
        this.auditoriumDao = new AuditoriumDao(auditoriumSet);
    }

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return auditoriumDao.getAll();
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return AuditoriumDao.getByName(name);
    }


}
