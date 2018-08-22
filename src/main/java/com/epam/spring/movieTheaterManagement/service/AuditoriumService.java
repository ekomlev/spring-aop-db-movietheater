package com.epam.spring.movieTheaterManagement.service;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.epam.spring.movieTheaterManagement.domain.Auditorium;

/**
 * @author Yuriy_Tkach
 */
public interface AuditoriumService {

    /**
     * Getting all auditoriums from the system
     * @return set of all auditoriums
     */
    public @Nonnull
    List<Auditorium> getAll();

    /**
     * Finding auditorium by name
     * @param name Name of the auditorium
     * @return found auditorium or <code>null</code>
     */
    public @Nullable
    Auditorium getByName(@Nonnull String name);
}
