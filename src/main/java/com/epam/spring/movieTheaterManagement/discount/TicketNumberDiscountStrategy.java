package com.epam.spring.movieTheaterManagement.discount;

import com.epam.spring.movieTheaterManagement.domain.Event;
import com.epam.spring.movieTheaterManagement.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component("ticketNumberDiscountStrategy")
@PropertySource("file:src/main/resources/discount.properties")
public class TicketNumberDiscountStrategy implements DiscountStrategy {

    @Value("${ticket.number}")
    private int ticketNumber;

    @Value("#{${ticket.number.discount}}")
    private double ticketNumberDiscount;

    @Override
    public double getDiscount(User user, Event event, LocalDateTime eventDateTime, Long numberOfTickets) {
        return Objects.isNull(user) ? getNotRegisteredUserDiscount(numberOfTickets)
                : getRegisteredUserDiscount(user, numberOfTickets);
    }

    private double getNotRegisteredUserDiscount(Long numberOfTickets) {
        long numberOfDiscountTickets = numberOfTickets / ticketNumber;
        return (numberOfDiscountTickets * ticketNumberDiscount / numberOfTickets);
    }

    private double getRegisteredUserDiscount(User user, Long numberOfTickets) {
        long numberOfExistingTickets = Objects.isNull(user.getTickets()) ? user.getTickets().size() : 0;
        long numberOfDiscountTickets = (numberOfExistingTickets + numberOfTickets) / ticketNumber;
        return (numberOfDiscountTickets * ticketNumberDiscount / numberOfTickets);
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public double getTicketNumberDiscount() {
        return ticketNumberDiscount;
    }

    public void setTicketNumberDiscount(byte ticketNumberDiscount) {
        this.ticketNumberDiscount = ticketNumberDiscount;
    }
}
