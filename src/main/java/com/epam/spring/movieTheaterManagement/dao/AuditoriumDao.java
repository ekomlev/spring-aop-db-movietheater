package com.epam.spring.movieTheaterManagement.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.epam.spring.movieTheaterManagement.domain.Auditorium;

@Repository
public class AuditoriumDao implements Dao<Auditorium> {
    private static final String AUDITORIUM_ID = "id";
    private static final String EVENT_NAME = "name";
    private static final String NUMBER_OF_SEATS = "numberOfSeats";
    private static final String VIP_SEATS = "vipSeats";
    private static final String SELECT_ALL_AUDITORIUMS_SQL = "SELECT * FROM t_auditorium";
    private static final String SELECT_AUDITORIUM_BY_NAME_SQL = "SELECT * FROM t_auditorium WHERE name=:name";
    private static final String SELECT_AUDITORIUM_BY_ID_SQL = "SELECT * FROM t_auditorium WHERE id=:id";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Auditorium> getAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_AUDITORIUMS_SQL, auditoriumMapRow());
    }

    public Auditorium getByName(String name) {
        return namedParameterJdbcTemplate.queryForObject(SELECT_AUDITORIUM_BY_NAME_SQL,
                new MapSqlParameterSource(EVENT_NAME, name), auditoriumMapRow());
    }

    public Auditorium getById(Long id) {
        return namedParameterJdbcTemplate.queryForObject(SELECT_AUDITORIUM_BY_ID_SQL,
                new MapSqlParameterSource(AUDITORIUM_ID, id), auditoriumMapRow());
    }

    private RowMapper<Auditorium> auditoriumMapRow() {

        return (rs, rowNum) -> {
            List<Long> vipSeats = new ArrayList<>();

            Arrays.stream(rs.getString(VIP_SEATS).split(","))
                    .forEach(i -> vipSeats.add(Long.valueOf(i)));

            return new Auditorium(
                    rs.getLong(AUDITORIUM_ID),
                    rs.getString(EVENT_NAME),
                    rs.getLong(NUMBER_OF_SEATS),
                    vipSeats
            );
        };
    }
}
