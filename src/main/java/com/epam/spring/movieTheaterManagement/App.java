package com.epam.spring.movieTheaterManagement;

import static java.lang.System.out;

import com.epam.spring.movieTheaterManagement.dao.AuditoriumDao;
import com.epam.spring.movieTheaterManagement.domain.Auditorium;
import com.epam.spring.movieTheaterManagement.domain.Event;
import com.epam.spring.movieTheaterManagement.domain.EventRating;
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
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/main/resources/app-context.xml");
        UserService theaterUserService = (UserService) ctx.getBean("theaterUserService");
        out.println("Users before: " + theaterUserService.getAll());

        AuditoriumService theaterAuditoriumService = (AuditoriumService) ctx.getBean("theaterAuditoriumService");
        out.println(theaterAuditoriumService.getAll());

        EventService theaterEventService = (EventService) ctx.getBean("theaterEventService");
        out.println("Events before: " + theaterEventService.getAll());
        theaterEventService.save(createFutureWorldEvent());
        out.println("Events after: " + theaterEventService.getAll());

        BookingService theaterBookingService = (BookingService) ctx.getBean("theaterBookingService");
        out.println("Booked tickets before: " + theaterBookingService
                .getPurchasedTicketsForEvent(theaterEventService.getByName("Future World"),
                        LocalDateTime.of(2018, 9, 27, 19, 0)));
    }

    private static Event createFutureWorldEvent() {
        NavigableSet<LocalDateTime> airDates = new TreeSet<>();
        airDates.add(LocalDateTime.of(2018, 9, 27, 19, 0));
        airDates.add(LocalDateTime.of(2018, 10, 24, 19, 0));

        NavigableMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();
        auditoriums.put(LocalDateTime.of(2018, 10, 24, 19, 0),
                AuditoriumDao.getByName("Small stage"));
        auditoriums.put(LocalDateTime.of(2018, 9, 27, 19, 0),
                AuditoriumDao.getByName("Big stage"));

        return new Event(1L, "Future World", airDates, 100.00,
                EventRating.HIGH, auditoriums);
    }

}
