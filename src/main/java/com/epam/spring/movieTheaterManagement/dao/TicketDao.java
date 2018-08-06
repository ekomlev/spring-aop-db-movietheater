package com.epam.spring.movieTheaterManagement.dao;

import com.epam.spring.movieTheaterManagement.domain.Ticket;

import java.util.*;

public class TicketDao implements Dao<Ticket> {
    private Set<Ticket> ticketList = new HashSet<>();

    public TicketDao() {}

    @Override
    public Set<Ticket> getAll() {
        return ticketList;
    }
}
