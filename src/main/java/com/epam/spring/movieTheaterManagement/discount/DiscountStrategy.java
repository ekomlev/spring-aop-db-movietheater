package com.epam.spring.movieTheaterManagement.discount;

import com.epam.spring.movieTheaterManagement.domain.Event;
import com.epam.spring.movieTheaterManagement.domain.User;

import java.time.LocalDateTime;

public interface DiscountStrategy {
    double getDiscount(User user, Event event, LocalDateTime eventDateTime, Long numberOfTickets);
}
