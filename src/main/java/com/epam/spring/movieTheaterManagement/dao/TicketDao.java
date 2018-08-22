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

import com.epam.spring.movieTheaterManagement.domain.Ticket;
import com.epam.spring.movieTheaterManagement.utils.LocalDateTimeConverter;

@Repository("ticketDao")
public class TicketDao implements Dao<Ticket> {
    private static final String TICKET_ID = "id";
    private static final String TICKET_DATE = "dateTime";
    private static final String TICKET_SEAT = "seat";
    private static final String TICKET_PRICE = "price";
    private static final String USER_ID = "user_id";
    private static final String EVENT_ID = "event_id";
    private static final String SELECT_ALL_TICKETS_SQL = "SELECT * FROM t_ticket";
    private static final String SAVE_TICKET_SQL = "INSERT into t_ticket (dateTime, seat, price, user_id, event_id) " +
            "VALUES (:dateTime, :seat, :price, :user_id, :event_id)";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    UserDao userDao;
    @Autowired
    EventDao eventDao;

    public Ticket save(@Nonnull Ticket ticket) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(TICKET_DATE, LocalDateTimeConverter.convertToString(ticket.getDateTime()));
        paramMap.put(TICKET_SEAT, ticket.getSeat());
        paramMap.put(TICKET_PRICE, ticket.getPrice());
        paramMap.put(USER_ID, ticket.getUser().getId());
        paramMap.put(EVENT_ID, ticket.getEvent().getId());

        namedParameterJdbcTemplate.update(SAVE_TICKET_SQL,
                new MapSqlParameterSource(paramMap), keyHolder);
        ticket.setId(keyHolder.getKey().longValue());

        return ticket;
    }

    @Override
    public List<Ticket> getAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_TICKETS_SQL, ticketMapRow());
    }

    private RowMapper<Ticket> ticketMapRow() {
        return (rs, rowNum) ->

                new Ticket(
                        rs.getLong(TICKET_ID),
                        userDao.getById(rs.getLong(USER_ID)),
                        eventDao.getById(rs.getLong(EVENT_ID)),
                        LocalDateTimeConverter.convertToLocalDateTime(rs.getString(TICKET_DATE)),
                        rs.getLong(TICKET_SEAT),
                        rs.getDouble(TICKET_PRICE)
                );
    }
}
