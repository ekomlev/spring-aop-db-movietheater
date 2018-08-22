package com.epam.spring.movieTheaterManagement.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epam.spring.movieTheaterManagement.domain.Event;
import com.epam.spring.movieTheaterManagement.domain.EventRating;
import com.epam.spring.movieTheaterManagement.utils.LocalDateTimeConverter;

@Repository("eventDao")
public class EventDao implements Dao<Event> {
    private static final String EVENT_ID = "id";
    private static final String EVENT_NAME = "name";
    private static final String EVENT_AIR_DATE = "airDate";
    private static final String EVENT_BASE_PRICE = "basePrice";
    private static final String EVENT_RAITING = "rating";
    private static final String AUDITORIUM_ID = "auditorium_id";
    private static final String SELECT_ALL_EVENTS_SQL = "SELECT * FROM t_event";
    private static final String SELECT_EVENT_BY_ID_SQL = "SELECT * FROM t_event WHERE id=:id";
    private static final String SELECT_EVENT_BY_NAME_SQL = "SELECT * FROM t_event WHERE name=:name";
    private static final String SAVE_EVENT_SQL = "INSERT into t_event (name, airDate, basePrice, rating, auditorium_id) " +
            "VALUES (:name, :airDate, :basePrice, :rating, :auditorium_id)";
    private static final String DELETE_EVENT_SQL = "DELETE FROM t_event WHERE id=:id";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    AuditoriumDao auditoriumDao;

    @Override
    public List<Event> getAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_EVENTS_SQL, eventMapRow());
    }

    public Event save(@Nonnull Event event) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(EVENT_NAME, event.getName());
        paramMap.put(EVENT_AIR_DATE, LocalDateTimeConverter.convertToString(event.getAirDate()));
        paramMap.put(EVENT_BASE_PRICE, event.getBasePrice());
        paramMap.put(EVENT_RAITING, event.getRating().toString());
        paramMap.put(AUDITORIUM_ID, event.getAuditorium().getId());

        namedParameterJdbcTemplate.update(SAVE_EVENT_SQL,
                new MapSqlParameterSource(paramMap), keyHolder);
        event.setId(keyHolder.getKey().longValue());

        return event;
    }

    public void remove(@Nonnull Event event) {
        namedParameterJdbcTemplate.update(DELETE_EVENT_SQL,
                new MapSqlParameterSource(EVENT_ID, event.getId()));
    }

    public Event getById(@Nonnull Long id) {
        return namedParameterJdbcTemplate.queryForObject(SELECT_EVENT_BY_ID_SQL,
                new MapSqlParameterSource(EVENT_ID, id), eventMapRow());
    }

    public Event getByName(@Nonnull String name) {
        return namedParameterJdbcTemplate.queryForObject(SELECT_EVENT_BY_NAME_SQL,
                new MapSqlParameterSource(EVENT_NAME, name), eventMapRow());
    }

    private RowMapper<Event> eventMapRow() {
        return (rs, rowNum) ->

                new Event(
                        rs.getLong(EVENT_ID),
                        rs.getString(EVENT_NAME),
                        LocalDateTimeConverter.convertToLocalDateTime(rs.getString(EVENT_AIR_DATE)),
                        rs.getDouble(EVENT_BASE_PRICE),
                        EventRating.valueOf(rs.getString(EVENT_RAITING).toUpperCase()),
                        auditoriumDao.getById(rs.getLong(AUDITORIUM_ID))
                );
    }
}
