package com.epam.spring.movieTheaterManagement.service;

import com.epam.spring.movieTheaterManagement.discount.DiscountStrategy;
import com.epam.spring.movieTheaterManagement.domain.Event;
import com.epam.spring.movieTheaterManagement.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;

public class TheaterDiscountService implements DiscountService {
    private List<DiscountStrategy> discountStrategyList;

    public TheaterDiscountService(List<DiscountStrategy> discountStrategyList) {
        this.discountStrategyList = discountStrategyList;
    }

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        return 0;
    }

    public List<DiscountStrategy> getDiscountStrategyList() {
        return discountStrategyList;
    }

    public void setDiscountStrategyList(List<DiscountStrategy> discountStrategyList) {
        this.discountStrategyList = discountStrategyList;
    }

    @Override
    public String toString() {
        return "TheaterDiscountService{" +
                "discountStrategyList=" + discountStrategyList +
                '}';
    }
}
