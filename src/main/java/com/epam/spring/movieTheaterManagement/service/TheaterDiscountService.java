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
    public double getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        double maxDiscount = 0;

        for (DiscountStrategy discountStrategy: discountStrategyList) {
            double currentDiscount = discountStrategy.getDiscount(user, event, airDateTime, numberOfTickets);
            if (currentDiscount > maxDiscount) {
                maxDiscount = currentDiscount;
            }
        }

        return maxDiscount;
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