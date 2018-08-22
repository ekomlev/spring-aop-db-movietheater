package com.epam.spring.movieTheaterManagement.discount;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.epam.spring.movieTheaterManagement.domain.Event;
import com.epam.spring.movieTheaterManagement.domain.User;

@Component("birthdayDiscountStrategy")
@PropertySource("file:src/main/resources/discount.properties")
public class BirthdayDiscountStrategy implements DiscountStrategy {
    private static final int EXPECTED_DAYS_DELTA = 5;

    @Value("#{${birthday.discount}}")
    private double birthdayDiscount;

    @Override
    public double getDiscount(User user, Event event, LocalDateTime eventDateTime, Long numberOfTickets) {
        if (Objects.isNull(user) || Objects.isNull(user.getBirthday()) || Objects.isNull(eventDateTime)) {
            return 0;
        } else {
            int actualDaysDelta = eventDateTime.getDayOfYear() - user.getBirthday().getDayOfYear();
            return (actualDaysDelta >= 0 && actualDaysDelta <= EXPECTED_DAYS_DELTA) ? birthdayDiscount : 0;
        }
    }

    public double getBirthdayDiscount() {
        return birthdayDiscount;
    }

    public void setBirthdayDiscount(double birthdayDiscount) {
        this.birthdayDiscount = birthdayDiscount;
    }
}
