package com.epam.spring.movieTheaterManagement.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Yuriy_Tkach
 */
public class Event extends DomainObject {
    private String name;
    private LocalDateTime airDate;
    private double basePrice;
    private EventRating rating;
    private Auditorium auditorium;

    public Event() {
    }

    public Event(String name, LocalDateTime airDate, double basePrice,
                 EventRating rating, Auditorium auditorium) {
        this.name = name;
        this.airDate = airDate;
        this.basePrice = basePrice;
        this.rating = rating;
        this.auditorium = auditorium;
    }

    public Event(Long id, String name, LocalDateTime airDate, double basePrice,
                 EventRating rating, Auditorium auditorium) {
        setId(id);
        this.name = name;
        this.airDate = airDate;
        this.basePrice = basePrice;
        this.rating = rating;
        this.auditorium = auditorium;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getAirDate() {
        return airDate;
    }

    public void setAirDate(LocalDateTime airDate) {
        this.airDate = airDate;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public EventRating getRating() {
        return rating;
    }

    public void setRating(EventRating rating) {
        this.rating = rating;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Event other = (Event) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", airDate=" + airDate +
                ", basePrice=" + basePrice +
                ", rating=" + rating +
                ", auditorium=" + auditorium +
                '}';
    }
}
