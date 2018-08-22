package com.epam.spring.movieTheaterManagement.discount;

import java.time.LocalDateTime;

import com.epam.spring.movieTheaterManagement.domain.Event;
import com.epam.spring.movieTheaterManagement.domain.User;

public interface DiscountStrategy {
    double getDiscount(User user, Event event, LocalDateTime eventDateTime, Long numberOfTickets);
}
