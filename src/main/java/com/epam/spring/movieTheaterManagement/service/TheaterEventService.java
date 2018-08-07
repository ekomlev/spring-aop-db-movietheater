package com.epam.spring.movieTheaterManagement.service;

import com.epam.spring.movieTheaterManagement.dao.EventDao;
import com.epam.spring.movieTheaterManagement.domain.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TheaterEventService implements EventService {
    private EventDao eventDao;

    public TheaterEventService() {
    }

    public TheaterEventService(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        return eventDao.getByName(name);
    }

    @Override
    public Event save(@Nonnull Event event) {
        return eventDao.save(event);
    }

    @Override
    public void remove(@Nonnull Event event) {
        eventDao.remove(event);
    }

    @Override
    public Event getById(@Nonnull Long id) {
        return eventDao.getById(id);
    }

    @Nonnull
    @Override
    public List<Event> getAll() {
        return eventDao.getAll();
    }
}
