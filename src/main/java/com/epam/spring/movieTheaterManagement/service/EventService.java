package com.epam.spring.movieTheaterManagement.service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.epam.spring.movieTheaterManagement.domain.Event;

/**
 * @author Yuriy_Tkach
 */
public interface EventService extends AbstractDomainObjectService<Event> {

    /**
     * Finding event by name
     * @param name Name of the event
     * @return found event or <code>null</code>
     */
    public @Nullable
    Event getByName(@Nonnull String name);
}
