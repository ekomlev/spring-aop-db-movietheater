package com.epam.spring.movieTheaterManagement;

import static java.lang.System.out;

import com.epam.spring.movieTheaterManagement.dao.AuditoriumDao;
import com.epam.spring.movieTheaterManagement.domain.*;
import com.epam.spring.movieTheaterManagement.service.AuditoriumService;
import com.epam.spring.movieTheaterManagement.service.BookingService;
import com.epam.spring.movieTheaterManagement.service.EventService;
import com.epam.spring.movieTheaterManagement.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.time.LocalDateTime;
import java.util.*;

public class App {
    public static void main(String[] args) {

        /**
         * Run app to verify beans
         */

        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/main/resources/app-context.xml");

        UserService theaterUserService = (UserService) ctx.getBean("theaterUserService");
        out.println("Users before: " + theaterUserService.getAll());
        createUsers(theaterUserService);
        out.println("Users after: " + theaterUserService.getAll());

        AuditoriumService theaterAuditoriumService = (AuditoriumService) ctx.getBean("theaterAuditoriumService");
        out.println("Auditoriums : " + theaterAuditoriumService.getAll());

        EventService theaterEventService = (EventService) ctx.getBean("theaterEventService");
        out.println("Events before: " + theaterEventService.getAll());
        createFutureWorldEvent(theaterEventService);
        out.println("Events after: " + theaterEventService.getAll());

        BookingService theaterBookingService = (BookingService) ctx.getBean("theaterBookingService");
        out.println("Booked tickets before: " + theaterBookingService
                .getPurchasedTicketsForEvent(theaterEventService.getByName("Future World"),
                        LocalDateTime.of(2018, 9, 27, 19, 0)));
        createTicketSet(theaterBookingService, theaterEventService, theaterUserService);
        out.println("Booked tickets before: " + theaterBookingService
                .getPurchasedTicketsForEvent(theaterEventService.getByName("Future World"),
                        LocalDateTime.of(2018, 9, 27, 19, 0)));
    }

    private static void createUsers(UserService theaterUserService) {
        theaterUserService.save(new User(1L, "Vasili", "Savchenco",
                "vs@mail.ru", LocalDateTime.of(2000, 10, 7, 19, 0)));
        theaterUserService.save(new User(2L, "Ivan", "Peftiev",
                "ip@mail.ru", LocalDateTime.of(1990, 9, 27, 19, 0)));
        theaterUserService.save(new User(2L, "Mariya", "Andronova",
                "ma@mail.ru", LocalDateTime.of(1990, 9, 27, 19, 0)));
    }

    private static void createFutureWorldEvent(EventService theaterEventService) {
        NavigableSet<LocalDateTime> airDates = new TreeSet<>();
        airDates.add(LocalDateTime.of(2018, 9, 27, 19, 0));
        airDates.add(LocalDateTime.of(2018, 10, 24, 19, 0));

        NavigableMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();
        auditoriums.put(LocalDateTime.of(2018, 10, 24, 19, 0),
                AuditoriumDao.getByName("Small stage"));
        auditoriums.put(LocalDateTime.of(2018, 9, 27, 19, 0),
                AuditoriumDao.getByName("Big stage"));

        theaterEventService.save(new Event(1L, "Future World", airDates, 100.00,
                EventRating.HIGH, auditoriums));
    }

    private static void createTicketSet(BookingService theaterBookingService, EventService theaterEventService, UserService theaterUserService) {
        Set<Ticket> ticketSet = new HashSet<>();
        Event event = theaterEventService.getByName("Future World");
        LocalDateTime airDate = LocalDateTime.of(2018, 9, 27, 19, 0);
        User user = theaterUserService.getById(1L);
        long seat = 2;
        ticketSet.add(new Ticket(user, event, airDate, seat));
        theaterBookingService.bookTickets(ticketSet);

    }
}
