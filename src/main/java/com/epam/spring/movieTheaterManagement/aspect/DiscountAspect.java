package com.epam.spring.movieTheaterManagement.aspect;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.spring.movieTheaterManagement.dao.CallCountDao;
import com.epam.spring.movieTheaterManagement.domain.Event;
import com.epam.spring.movieTheaterManagement.domain.User;

@Aspect
@Component("discountAspect")
public class DiscountAspect {
    private Map<Class<?>, Map<User, Long>> ticketDiscountCallCounter = new HashMap<>();
    Map<User, Long> userMap = new HashMap<>();
    Map<Event, Long> eventTicketDiscountCallCounter = new HashMap<>();

    @Autowired
    CallCountDao callCountDao;

    @Pointcut(
            "execution(" +
                    "* com.epam.spring.movieTheaterManagement.discount.DiscountStrategy.getDiscount(..))")
    public void getDiscountStrategy() {
    }

    @AfterReturning(pointcut = "getDiscountStrategy()")
    public void getDiscountStrategyCount(JoinPoint joinPoint) {

        if (joinPoint.getArgs()[0] instanceof User && joinPoint.getArgs()[1] instanceof Event) {
            User user = (User) joinPoint.getArgs()[0];
            Event event = (Event) joinPoint.getArgs()[1];

            Class<?> clazz = joinPoint.getTarget().getClass();

            if (!ticketDiscountCallCounter.containsKey(clazz)
                    || Objects.isNull(ticketDiscountCallCounter.get(clazz).get(user))) {
                userMap.put(user, 0L);
                ticketDiscountCallCounter.put(clazz, userMap);
                System.out.println("getDiscount is called at first time " + joinPoint.getTarget().getClass().getSimpleName());
                eventTicketDiscountCallCount(event);

            } else {
                Map<User, Long> userMap = ticketDiscountCallCounter.get(clazz);
                userMap.put(user, userMap.get(user) + 1);

                ticketDiscountCallCounter.put(clazz, userMap);

                eventTicketDiscountCallCount(event);

                callCountDao.updateDiscountCounterByEvent(event, eventTicketDiscountCallCounter.getOrDefault(event, 0L));

                System.out.println("getDiscount is called" + joinPoint.getTarget().getClass().getSimpleName());
            }
        }
    }

    private void eventTicketDiscountCallCount(Event event) {
        long currentCount = eventTicketDiscountCallCounter.getOrDefault(event, 0L);
        eventTicketDiscountCallCounter.put(event, currentCount + 1L);
    }

    public Map<Class<?>, Map<User, Long>> getTicketDiscountCallCounter() {
        return Collections.unmodifiableMap(ticketDiscountCallCounter);
    }

    public void setTicketDiscountCallCounter(Map<Class<?>, Map<User, Long>> ticketDiscountCallCounter) {
        this.ticketDiscountCallCounter = ticketDiscountCallCounter;
    }
}
