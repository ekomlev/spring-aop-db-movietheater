package com.epam.spring.movieTheaterManagement.dao;

import com.epam.spring.movieTheaterManagement.domain.Auditorium;
import com.epam.spring.movieTheaterManagement.domain.Event;
import com.epam.spring.movieTheaterManagement.domain.EventRating;
import com.epam.spring.movieTheaterManagement.domain.User;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

public class EventDao implements Dao<Event> {
    private static List<Event> eventsList = new ArrayList<>();

    public EventDao() {}

    /*public EventDao(List<Event> eventsList) {
        this.eventsList = eventsList;
    }*/

    public Event createEvent(Long id, String name, NavigableSet<LocalDateTime> airDates, double basePrice,
                            EventRating rating, NavigableMap<LocalDateTime, Auditorium> auditoriums) {

        Event newEvent = new Event(id, name, airDates, basePrice, rating,auditoriums);
        eventsList.add(newEvent);
        return newEvent;
    }

    @Override
    public List<Event> getAll() {
        return eventsList;
    }

    public Event getByName(@Nonnull String name) {
        return eventsList
                .stream()
                .filter(event -> event.getName().equalsIgnoreCase(name))
                .findAny()
                .orElse(null);
    }

    public Event getById(@Nonnull Long id) {
        return eventsList
                .stream()
                .filter(event -> event.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public Event save(@Nonnull Event event) {
        eventsList.add(event);
        return event;
    }

    public boolean remove(@Nonnull Event event) {
        return eventsList.remove(event);
    }

    public boolean removeById(@Nonnull Long id) {
        Predicate<Event> eventPredicate = p -> p.getId().equals(id);
        return eventsList.removeIf(eventPredicate);
    }
}
