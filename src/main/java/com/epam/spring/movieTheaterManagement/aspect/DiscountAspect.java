package com.epam.spring.movieTheaterManagement.aspect;

import com.epam.spring.movieTheaterManagement.domain.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Aspect
@EnableAspectJAutoProxy
@Component("discountAspect")
public class DiscountAspect {
    private Map<Class<?>, Map<User, Integer>> discountCounter = new HashMap<>();
    Map<User, Integer> userMap = new HashMap<>();

    @Pointcut(
            "execution(" +
                    "* com.epam.spring.movieTheaterManagement.discount.DiscountStrategy.getDiscount(..))")
    public void getDiscountStrategy() {
    }

    @AfterReturning(pointcut = "getDiscountStrategy()")
    public void getDiscountStrategyCount(JoinPoint joinPoint) {

        if (joinPoint.getArgs()[0] instanceof User) {
            User user = (User) joinPoint.getArgs()[0];

            Class<?> clazz = joinPoint.getTarget().getClass();

            if (!discountCounter.containsKey(clazz)
                    || Objects.isNull(discountCounter.get(clazz).get(user))) {
                userMap.put(user, 0);
                discountCounter.put(clazz, userMap);
                System.out.println("getDiscount is called at first time " + joinPoint.getTarget().getClass().getSimpleName());

            } else {
                Map<User, Integer> userMap = discountCounter.get(clazz);
                userMap.put(user, userMap.get(user) + 1);

                discountCounter.put(clazz, userMap);
                System.out.println("getDiscount is called");
            }
        }
    }

    public Map<Class<?>, Map<User, Integer>> getDiscountCounter() {
        return Collections.unmodifiableMap(discountCounter);
    }

    public void setDiscountCounter(Map<Class<?>, Map<User, Integer>> discountCounter) {
        this.discountCounter = discountCounter;
    }
}
