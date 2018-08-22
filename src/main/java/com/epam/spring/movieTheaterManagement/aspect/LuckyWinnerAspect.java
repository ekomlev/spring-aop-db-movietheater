package com.epam.spring.movieTheaterManagement.aspect;

import java.util.List;
import java.util.Random;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.epam.spring.movieTheaterManagement.domain.Ticket;
import com.epam.spring.movieTheaterManagement.service.BookingService;

@Aspect
@Component("luckyWinnerAspect")
public class LuckyWinnerAspect {

    @Pointcut(
            "execution(" +
                    "* com.epam.spring.movieTheaterManagement.service.BookingService.bookTickets(..))")
    public void ticketBooked() {
    }

    @Around("ticketBooked()")
    public void getLuckyWinnerUser(ProceedingJoinPoint joinPoint) throws Throwable {
        BookingService bookingService = (BookingService) joinPoint.getThis();

        List<Ticket> bookedTicketsSetBefore = bookingService.getAllTickets();
        joinPoint.proceed(joinPoint.getArgs());
        List<Ticket> bookedTicketsSetAfter = bookingService.getAllTickets();

        bookedTicketsSetAfter.stream()
                .filter(ticket -> !bookedTicketsSetBefore.contains(ticket))
                .forEach(ticket -> {
                    if (nextInt(100) == 100) {
                        ticket.setPrice(0D);
                    }
                });
    }

    private int nextInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }
}
