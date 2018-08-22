package com.epam.spring.movieTheaterManagement.aspect;

import java.util.*;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.spring.movieTheaterManagement.dao.CallCountDao;
import com.epam.spring.movieTheaterManagement.domain.Event;
import com.epam.spring.movieTheaterManagement.domain.Ticket;

@Aspect
@Component("counterAspect")
public class CounterAspect {
    private Map<Event, Long> eventByNameCallCounter = new HashMap<>();
    private Map<Event, Long> eventTicketPriceCallCounter = new HashMap<>();
    private Map<Event, Long> eventTicketBookingCallCounter = new HashMap<>();

    @Autowired
    CallCountDao callCountDao;

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
    public void getBookedTicketsEvent(List<Ticket> tickets) {
    }

    @AfterReturning(pointcut = "getByNameEvent()", returning = "event")
    public void eventByNameCall(Event event) {
        callsCount(eventByNameCallCounter, event);
        System.out.println("Event " + event.getName() + " is called");
    }

    @AfterReturning(pointcut = "getTicketsPriceEvent(event)", argNames = "event")
    public void eventTicketPriceCallCount(Event event) {
        callsCount(eventTicketPriceCallCounter, event);
        System.out.println("TicketsPriceEvent is called, event: " + event.getName());
    }

    @AfterReturning(pointcut = "getBookedTicketsEvent(tickets)", argNames = "tickets")
    public void eventTicketBookingCallCount(List<Ticket> tickets) {
        Set<Event> events = new HashSet<>();
        tickets
                .forEach(ticket -> events.add(ticket.getEvent()));

        events
                .forEach(event -> {
                    callsCount(eventTicketBookingCallCounter, event);
                    System.out.println("BookedTicketsEvent is called, event: " + event.getName());
                });
    }

    private void callsCount(Map<Event, Long> counterMap, Event event) {
        long currentCount = counterMap.getOrDefault(event, 0L);
        counterMap.put(event, currentCount + 1L);

        callCountDao.updateCounterByEvent(event,
                eventByNameCallCounter.getOrDefault(event, 0L),
                eventTicketPriceCallCounter.getOrDefault(event, 0L),
                eventTicketBookingCallCounter.getOrDefault(event, 0L));
    }

    public Map<Event, Long> getEventByNameCallCounter() {
        return Collections.unmodifiableMap(eventByNameCallCounter);
    }

    public void setEventByNameCallCounter(Map<Event, Long> eventByNameCallCounter) {
        this.eventByNameCallCounter = eventByNameCallCounter;
    }

    public Map<Event, Long> getEventTicketPriceCallCounter() {
        return Collections.unmodifiableMap(eventTicketPriceCallCounter);
    }

    public void setEventTicketPriceCallCounter(Map<Event, Long> eventTicketPriceCallCounter) {
        this.eventTicketPriceCallCounter = eventTicketPriceCallCounter;
    }

    public Map<Event, Long> getEventTicketBookingCallCounter() {
        return Collections.unmodifiableMap(eventTicketBookingCallCounter);
    }

    public void setEventTicketBookingCallCounter(Map<Event, Long> eventTicketBookingCallCounter) {
        this.eventTicketBookingCallCounter = eventTicketBookingCallCounter;
    }
}
