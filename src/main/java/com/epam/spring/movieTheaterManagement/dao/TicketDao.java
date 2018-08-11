package com.epam.spring.movieTheaterManagement.dao;

import com.epam.spring.movieTheaterManagement.domain.Ticket;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

@Component("ticketDao")
public class TicketDao implements Dao<Ticket> {
    private Set<Ticket> ticketList = new HashSet<>();

    public Ticket save(@Nonnull Ticket ticket) {
        ticketList.add(ticket);
        return ticket;
    }

    @Override
    public Set<Ticket> getAll() {
        return ticketList;
    }
}
