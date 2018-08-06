package com.epam.spring.movieTheaterManagement.service;

import com.epam.spring.movieTheaterManagement.dao.TicketDao;
import com.epam.spring.movieTheaterManagement.domain.Event;
import com.epam.spring.movieTheaterManagement.domain.Ticket;
import com.epam.spring.movieTheaterManagement.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class TheaterBookingService implements BookingService {
    private TicketDao ticketDao;
    private UserService userService;
    private DiscountService discountService;

    public TheaterBookingService(TicketDao ticketDao, UserService userService,
                                 DiscountService discountService) {
        this.ticketDao = ticketDao;
        this.userService = userService;
        this.discountService = discountService;
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable
            User user, @Nonnull Set<Long> seats) {

        return 0;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {

    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        Set<Ticket> purchasedTickets = new HashSet<>();
        return purchasedTickets;
    }
}
