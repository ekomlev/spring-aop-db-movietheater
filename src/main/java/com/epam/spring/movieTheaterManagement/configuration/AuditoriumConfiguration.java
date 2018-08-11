package com.epam.spring.movieTheaterManagement.configuration;

import com.epam.spring.movieTheaterManagement.domain.Auditorium;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Set;

@Configuration
@PropertySource("file:src/main/resources/auditorium.properties")
@ComponentScan(basePackages = {"com.epam.spring.movieTheaterManagement.service"})
public class AuditoriumConfiguration {
    @Value("${auditorium1.name}")
    private String smallAuditoriumName;
    @Value("${auditorium1.numberOfSeats}")
    private long smallAuditoriumNumberOfSeats;
    @Value("#{'${auditorium1.vipSeats}'.split(',')}")
    private Set<Long> smallAuditoriumVipSeats;

    @Value("${auditorium2.name}")
    private String bigAuditoriumName;
    @Value("${auditorium2.numberOfSeats}")
    private long bigAuditoriumNumberOfSeats;
    @Value("#{'${auditorium2.vipSeats}'.split(',')}")
    private Set<Long> bigAuditoriumVipSeats;

    @Bean
    public Auditorium smallAuditorium() {
        Auditorium auditorium = new Auditorium();
        auditorium.setName(smallAuditoriumName);
        auditorium.setNumberOfSeats(smallAuditoriumNumberOfSeats);
        auditorium.setVipSeats(smallAuditoriumVipSeats);

        return auditorium;
    }

    @Bean
    public Auditorium bigAuditorium() {
        Auditorium auditorium = new Auditorium();
        auditorium.setName(bigAuditoriumName);
        auditorium.setNumberOfSeats(bigAuditoriumNumberOfSeats);
        auditorium.setVipSeats(bigAuditoriumVipSeats);

        return auditorium;
    }
}
