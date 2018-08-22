package com.epam.spring.movieTheaterManagement.service;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.spring.movieTheaterManagement.dao.AuditoriumDao;
import com.epam.spring.movieTheaterManagement.domain.Auditorium;

@Service("theaterAuditoriumService")
public class TheaterAuditoriumService implements AuditoriumService {
    @Autowired
    private AuditoriumDao auditoriumDao;

    @Nonnull
    @Override
    public List<Auditorium> getAll() {
        return auditoriumDao.getAll();
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return auditoriumDao.getByName(name);
    }
}
