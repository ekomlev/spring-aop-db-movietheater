package com.epam.spring.movieTheaterManagement.aspect;

import com.epam.spring.movieTheaterManagement.domain.Event;
import com.epam.spring.movieTheaterManagement.domain.Ticket;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.*;

@Aspect
@EnableAspectJAutoProxy
@Component("counterAspect")
public class CounterAspect {
    private Map<Event, Long> nameEventCounter = new HashMap<>();
    private Map<Event, Long> ticketsPriceEventCounter = new HashMap<>();
    private Map<Event, Long> bookedTicketsEventCounter = new HashMap<>();

    @Pointcut(
            "execution(" +
                    "* com.epam.spring.movieTheaterManagement.service.EventService.getByName(..))")
    public void getByNameEvent() {
    }

    @Pointcut(
            "execution(" +
                    "* com.epam.spring.movieTheaterManagement.service.BookingService.getTicketsPrice(..)) " +
                    "&& args(event, ..)")
    public void getTicketsPriceEvent(Event event) {
    }

    @Pointcut(
            "execution(" +
                    "* com.epam.spring.movieTheaterManagement.service.BookingService.bookTickets(..)) " +
                    "&& args(tickets, ..)")
    public void getBookedTicketsEvent(Set<Ticket> tickets) {
    }

    @AfterReturning(pointcut = "getByNameEvent()", returning = "event")
    public void eventCallsCount(Event event) {
        callsCount(nameEventCounter, event);
        System.out.println("Event is called");
    }

    @AfterReturning(pointcut = "getTicketsPriceEvent(event)", argNames = "event")
    public void ticketsPriceEventCallsCount(Event event) {
        callsCount(ticketsPriceEventCounter, event);
        System.out.println("TicketsPriceEvent is called");
    }

    @AfterReturning(pointcut = "getBookedTicketsEvent(tickets)", argNames = "tickets")
    public void bookedTicketsEventCallsCount(Set<Ticket> tickets) {
        Set<Event> events = new HashSet<>();
        tickets
                .forEach(ticket -> events.add(ticket.getEvent()));

        events
                .forEach(event -> {
                    callsCount(bookedTicketsEventCounter, event);
                    System.out.println("BookedTicketsEvent is called");
                });
    }

    private void callsCount(Map<Event, Long> counterMap, Event event) {
        long currentCount = counterMap.getOrDefault(event, 0L);
        counterMap.put(event, currentCount + 1L);
    }

    public Map<Event, Long> getNameEventCounter() {
        return Collections.unmodifiableMap(nameEventCounter);
    }

    public void setNameEventCounter(Map<Event, Long> nameEventCounter) {
        this.nameEventCounter = nameEventCounter;
    }

    public Map<Event, Long> getTicketsPriceEventCounter() {
        return Collections.unmodifiableMap(ticketsPriceEventCounter);
    }

    public void setTicketsPriceEventCounter(Map<Event, Long> ticketsPriceEventCounter) {
        this.ticketsPriceEventCounter = ticketsPriceEventCounter;
    }

    public Map<Event, Long> getBookedTicketsEventCounter() {
        return Collections.unmodifiableMap(bookedTicketsEventCounter);
    }

    public void setBookedTicketsEventCounter(Map<Event, Long> bookedTicketsEventCounter) {
        this.bookedTicketsEventCounter = bookedTicketsEventCounter;
    }
}
