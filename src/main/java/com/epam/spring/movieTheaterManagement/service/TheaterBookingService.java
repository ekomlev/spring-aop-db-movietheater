package com.epam.spring.movieTheaterManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.epam.spring.movieTheaterManagement.dao.TicketDao;
import com.epam.spring.movieTheaterManagement.domain.*;

@Service("theaterBookingService")
@PropertySource("file:src/main/resources/price.properties")
public class TheaterBookingService implements BookingService {

    @Value("#{${high.event.price.rate}}")
    private double highEventPriceRate;
    @Value("#{${vip.seats.price.rate}}")
    private double vipSeatsPriceRate;
    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private UserService userService;
    @Autowired
    private DiscountService discountService;

    public TheaterBookingService() {
    }

    public TheaterBookingService(double highEventPriceRate, double vipSeatsPriceRate,
                                 TicketDao ticketDao, UserService userService,
                                 DiscountService discountService) {
        this.highEventPriceRate = highEventPriceRate;
        this.vipSeatsPriceRate = vipSeatsPriceRate;
        this.ticketDao = ticketDao;
        this.userService = userService;
        this.discountService = discountService;
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable
            User user, @Nonnull Set<Long> seats) {
        double basePrice = event.getBasePrice();
        double totalPrice = 0.0;
        double discount = discountService.getDiscount(user, event, dateTime, seats.size());

        if (Objects.isNull(event.getAuditorium())) {
            totalPrice = getTicketsPriceWithVipSeatsRate(event, dateTime, seats, basePrice);
        }

        totalPrice = increasePriceWithHighEventRate(event, totalPrice);
        totalPrice = decreasePriceWithDiscount(totalPrice, discount);

        return totalPrice;
    }

    private double getTicketsPriceWithVipSeatsRate(Event event, LocalDateTime dateTime, Set<Long> seats, double price) {
        Auditorium auditorium = event.getAuditorium();
        long countSeats = seats.size();
        long countVipSeats = auditorium.countVipSeats(seats);
        double vipPrice = price * vipSeatsPriceRate;

        return price * (countSeats - countVipSeats) + vipPrice * countVipSeats;

    }

    private double increasePriceWithHighEventRate(Event event, double totalPrice) {
        return event.getRating() == EventRating.HIGH ? totalPrice * highEventPriceRate : totalPrice;
    }

    private double decreasePriceWithDiscount(double totalPrice, double discount) {
        return totalPrice - totalPrice * discount;
    }

    @Override
    public void bookTickets(@Nonnull List<Ticket> tickets) {
        tickets
                .forEach(ticket -> {
                    ticketDao.save(ticket);
                    if (Objects.nonNull(ticket.getUser())) {
                        ticket.getUser().addTicket(ticket);
                    }
                });
    }

    @Nonnull
    @Override
    public List<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {

        return ticketDao.getAll().stream()
                .filter(ticket -> event.equals(ticket.getEvent()) && dateTime.equals(ticket.getDateTime()))
                .collect(Collectors.toList());
    }

    @Nonnull
    @Override
    public List<Ticket> getAllTickets() {
        return ticketDao.getAll();
    }

    public double getHighEventPriceRate() {
        return highEventPriceRate;
    }

    public void setHighEventPriceRate(double highEventPriceRate) {
        this.highEventPriceRate = highEventPriceRate;
    }

    public double getVipSeatsPriceRate() {
        return vipSeatsPriceRate;
    }

    public void setVipSeatsPriceRate(double vipSeatsPriceRate) {
        this.vipSeatsPriceRate = vipSeatsPriceRate;
    }

    public TicketDao getTicketDao() {
        return ticketDao;
    }

    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public DiscountService getDiscountService() {
        return discountService;
    }

    public void setDiscountService(DiscountService discountService) {
        this.discountService = discountService;
    }
}
