package com.epam.spring.movieTheaterManagement.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class LocalDateTimeConverter {
    private final static String dateFormat = "yyyy-MM-dd HH:mm";
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

    public static LocalDateTime convertToLocalDateTime(String source) {
        if (Objects.isNull(source) || source.isEmpty()) {
            return null;
        }

        return LocalDateTime.parse(source, formatter);
    }

    public static String convertToString(LocalDateTime source) {
        if (Objects.isNull(source)) {
            return null;
        }

        return source.format(formatter);
    }
}
