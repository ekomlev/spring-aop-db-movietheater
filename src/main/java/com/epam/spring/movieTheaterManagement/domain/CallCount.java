package com.epam.spring.movieTheaterManagement.domain;

public class CallCount extends DomainObject {
    private Event event;
    private long eventByNameCallCounter;
    private long eventTicketPriceCallCounter;
    private long eventTicketBookingCallCounter;
    private long ticketDiscountCallCounter;


    public CallCount(Long id, Event event, long eventByNameCallCounter, long ticketsPriceEventCount,
                     long eventTicketBookingCallCounter, long ticketDiscountCallCounter) {
        setId(id);
        this.event = event;
        this.eventByNameCallCounter = eventByNameCallCounter;
        this.eventTicketPriceCallCounter = ticketsPriceEventCount;
        this.eventTicketBookingCallCounter = eventTicketBookingCallCounter;
        this.ticketDiscountCallCounter = ticketDiscountCallCounter;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(String eventName) {
        this.event = event;
    }

    public long getEventByNameCallCounter() {
        return eventByNameCallCounter;
    }

    public void setEventByNameCallCounter(long eventByNameCallCounter) {
        this.eventByNameCallCounter = eventByNameCallCounter;
    }

    public long getEventTicketPriceCallCounter() {
        return eventTicketPriceCallCounter;
    }

    public void setEventTicketPriceCallCounter(long eventTicketPriceCallCounter) {
        this.eventTicketPriceCallCounter = eventTicketPriceCallCounter;
    }

    public long getEventTicketBookingCallCounter() {
        return eventTicketBookingCallCounter;
    }

    public void setEventTicketBookingCallCounter(long eventTicketBookingCallCounter) {
        this.eventTicketBookingCallCounter = eventTicketBookingCallCounter;
    }

    public long getTicketDiscountCallCounter() {
        return ticketDiscountCallCounter;
    }

    public void setTicketDiscountCallCounter(long ticketDiscountCallCounter) {
        this.ticketDiscountCallCounter = ticketDiscountCallCounter;
    }

    @Override
    public String toString() {
        return "Event Name: '" + event.getName() + '\'' +
                ", count of calls to event: " + eventByNameCallCounter +
                ", count of calls to ticket price: " + eventTicketPriceCallCounter +
                ", count of calls to booked tickets after: " + eventTicketBookingCallCounter +
                ", count applied discounts: " + ticketDiscountCallCounter;
    }
}
