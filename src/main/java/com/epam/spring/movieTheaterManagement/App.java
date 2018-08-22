package com.epam.spring.movieTheaterManagement;

import static java.lang.System.out;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.epam.spring.movieTheaterManagement.aspect.DiscountAspect;
import com.epam.spring.movieTheaterManagement.dao.CallCountDao;
import com.epam.spring.movieTheaterManagement.domain.Event;
import com.epam.spring.movieTheaterManagement.domain.EventRating;
import com.epam.spring.movieTheaterManagement.domain.Ticket;
import com.epam.spring.movieTheaterManagement.domain.User;
import com.epam.spring.movieTheaterManagement.service.AuditoriumService;
import com.epam.spring.movieTheaterManagement.service.BookingService;
import com.epam.spring.movieTheaterManagement.service.EventService;
import com.epam.spring.movieTheaterManagement.service.UserService;

public class App {

    public static void main(String[] args) {

        /**
         * Run app to verify beans
         */
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("com.epam.spring.movieTheaterManagement");
        ctx.refresh();

        UserService theaterUserService = (UserService) ctx.getBean("theaterUserService");
        out.println("Users before: " + theaterUserService.getAll());
        createUsers(theaterUserService);
        out.println("Get user by id = 2: " + theaterUserService.getById(2L));
        out.println("Get user by email = ma@mail.ru: " + theaterUserService.getUserByEmail("ma@mail.ru"));
        out.println("Get all users: " + theaterUserService.getAll());
        theaterUserService.remove(theaterUserService.getById(3L));
        out.println("Remove user by id = 3");
        out.println("Get all users after removing: " + theaterUserService.getAll());

        AuditoriumService theaterAuditoriumService = (AuditoriumService) ctx.getBean("theaterAuditoriumService");
        out.println("Get all auditoriums : " + theaterAuditoriumService.getAll());

        EventService theaterEventService = (EventService) ctx.getBean("theaterEventService");
        out.println("Events before: " + theaterEventService.getAll());
        createFutureWorldEvent(theaterEventService, theaterAuditoriumService);
        out.println("Get all created events: " + theaterEventService.getAll());

        BookingService theaterBookingService = (BookingService) ctx.getBean("theaterBookingService");
        out.println("Booked tickets before: " + theaterBookingService
                .getPurchasedTicketsForEvent(theaterEventService.getByName("Future World"),
                        LocalDateTime.of(2018, Month.SEPTEMBER, 27, 19, 0)));
        createTicketSet(theaterBookingService, theaterEventService, theaterUserService);
        out.println("Booked tickets after: " + theaterBookingService
                .getPurchasedTicketsForEvent(theaterEventService.getByName("Future World"),
                        LocalDateTime.of(2018, Month.SEPTEMBER, 27, 19, 0)));

        theaterBookingService.getTicketsPrice(theaterEventService.getByName("Future World"),
                LocalDateTime.of(2018, Month.SEPTEMBER, 27, 19, 0),
                theaterUserService.getById(1L),
                Stream.of(1L, 2L).collect(Collectors.toSet()));

        DiscountAspect discountAspect = (DiscountAspect) ctx.getBean("discountAspect");
        out.println("Count of calls to getDiscount after: " + discountAspect.getTicketDiscountCallCounter().size());
        out.println();
        CallCountDao callCountDao = (CallCountDao) ctx.getBean("callCountDao");
        out.println(callCountDao.getCounterByEventName("Future World"));
    }

    private static void createUsers(UserService theaterUserService) {
        theaterUserService.save(new User("Vasili", "Savchenco",
                "vs@mail.ru", LocalDateTime.of(2000, Month.OCTOBER, 7, 19, 0)));
        theaterUserService.save(new User("Ivan", "Peftiev",
                "ip@mail.ru", LocalDateTime.of(1990, Month.SEPTEMBER, 27, 19, 0)));
        theaterUserService.save(new User("Mariya", "Andronova",
                "ma@mail.ru", LocalDateTime.of(1990, Month.SEPTEMBER, 27, 19, 0)));
    }

    private static void createFutureWorldEvent(EventService theaterEventService, AuditoriumService theaterAuditoriumService) {
        theaterEventService.save(new Event("Future World",
                LocalDateTime.of(2018, Month.SEPTEMBER, 27, 19, 0), 100.00,
                EventRating.HIGH, theaterAuditoriumService.getByName("Big stage")));

        theaterEventService.save(new Event("Hold the Dark",
                LocalDateTime.of(2018, Month.OCTOBER, 7, 19, 0), 120.00,
                EventRating.LOW, theaterAuditoriumService.getByName("Small stage")));
    }

    private static void createTicketSet(BookingService theaterBookingService, EventService theaterEventService, UserService theaterUserService) {
        List<Ticket> ticketList = new ArrayList<>();
        Event event = theaterEventService.getByName("Future World");
        LocalDateTime airDate = LocalDateTime.of(2018, Month.SEPTEMBER, 27, 19, 0);
        User user = theaterUserService.getById(1L);
        long seat = 2;
        double price = event.getBasePrice();
        ticketList.add(new Ticket(user, event, airDate, seat, price));
        theaterBookingService.bookTickets(ticketList);
    }
}
