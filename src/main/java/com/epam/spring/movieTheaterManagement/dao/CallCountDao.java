package com.epam.spring.movieTheaterManagement.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.epam.spring.movieTheaterManagement.domain.CallCount;
import com.epam.spring.movieTheaterManagement.domain.Event;

@Repository("callCountDao")
public class CallCountDao implements Dao<CallCount> {
    private static final String TICKET_ID = "id";
    private static final String EVENT_ID = "event_id";
    private static final String EVENT_BY_NAME_CALL_COUNT = "eventByNameCallCounter";
    private static final String TICKET_PRICE_CALL_COUNT = "eventTicketPriceCallCounter";
    private static final String TICKET_BOOKING_CALL_COUNT = "eventTicketBookingCallCounter";
    private static final String TICKET_DISCOUNT_CALL_COUNT = "ticketDiscountCallCounter";
    private static final String SELECT_COUNTER_BY_EVENT_ID_SQL = "SELECT * FROM t_counter WHERE event_id=:event_id";
    private static final String SELECT_ALL_COUNTERS = "SELECT * FROM t_counter";
    private static final String UPDATE_COUNTER_SQL = "UPDATE t_counter SET eventByNameCallCounter=:eventByNameCallCounter, " +
            "eventTicketPriceCallCounter=:eventTicketPriceCallCounter, eventTicketBookingCallCounter=:eventTicketBookingCallCounter " +
            "WHERE event_id=:event_id";
    private static final String INSERT_COUNTER_SQL = "INSERT into t_counter (event_id, eventByNameCallCounter, eventTicketPriceCallCounter, " +
            "eventTicketBookingCallCounter) VALUES (:event_id, :eventByNameCallCounter, :eventTicketPriceCallCounter, :eventTicketBookingCallCounter)";
    private static final String SELECT_AMOUNT_BY_EVENT_ID_SQL = "SELECT COUNT(event_id) FROM t_counter WHERE event_id=:event_id";
    private Long eventByNameCallCounter = 0L;
    private Long eventTicketPriceCallCounter = 0L;
    private Long eventTicketBookingCallCounter = 0L;
    private Long ticketDiscountCallCounter = 0L;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private EventDao eventDao;

    @Override
    public List<CallCount> getAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_COUNTERS, counterMapRow());
    }

    public void updateCounterByEvent(Event event, long eventByNameCallCounter, long eventTicketPriceCallCounter,
                                     long eventTicketBookingCallCounter) {
        this.eventByNameCallCounter = eventByNameCallCounter;
        this.eventTicketPriceCallCounter = eventTicketPriceCallCounter;
        this.eventTicketBookingCallCounter = eventTicketBookingCallCounter;

        Long eventId = event.getId();

        Map<String, Object> params = new HashMap();
        params.put(EVENT_ID, eventId);
        params.put(EVENT_BY_NAME_CALL_COUNT, eventByNameCallCounter);
        params.put(TICKET_PRICE_CALL_COUNT, eventTicketPriceCallCounter);
        params.put(TICKET_BOOKING_CALL_COUNT, eventTicketBookingCallCounter);
        params.put(TICKET_DISCOUNT_CALL_COUNT, ticketDiscountCallCounter);

        if (!isExistsByEventId(eventId)) {
            namedParameterJdbcTemplate.update(INSERT_COUNTER_SQL, params);
        } else {
            namedParameterJdbcTemplate.update(UPDATE_COUNTER_SQL, params);
        }
    }

    public void updateDiscountCounterByEvent(Event event, long ticketDiscountCallCounter) {

        this.ticketDiscountCallCounter = ticketDiscountCallCounter;

        Long eventId = event.getId();

        Map<String, Object> params = new HashMap();
        params.put(EVENT_ID, eventId);
        params.put(EVENT_BY_NAME_CALL_COUNT, eventByNameCallCounter);
        params.put(TICKET_PRICE_CALL_COUNT, eventTicketPriceCallCounter);
        params.put(TICKET_BOOKING_CALL_COUNT, eventTicketBookingCallCounter);
        params.put(TICKET_DISCOUNT_CALL_COUNT, ticketDiscountCallCounter);

        if (!isExistsByEventId(eventId)) {
            namedParameterJdbcTemplate.update(INSERT_COUNTER_SQL, params);
        } else {
            namedParameterJdbcTemplate.update(UPDATE_COUNTER_SQL, params);
        }
    }

    public CallCount getCounterByEventName(String eventName) {
        Long eventId = eventDao.getByName(eventName).getId();

        return namedParameterJdbcTemplate.queryForObject(SELECT_COUNTER_BY_EVENT_ID_SQL,
                new MapSqlParameterSource(EVENT_ID, eventId), counterMapRow());
    }

    private RowMapper<CallCount> counterMapRow() {
        return (rs, rowNum) ->
                new CallCount(
                        rs.getLong(TICKET_ID),
                        eventDao.getById(rs.getLong(EVENT_ID)),
                        rs.getLong(EVENT_BY_NAME_CALL_COUNT),
                        rs.getLong(TICKET_PRICE_CALL_COUNT),
                        rs.getLong(TICKET_BOOKING_CALL_COUNT),
                        rs.getLong(TICKET_DISCOUNT_CALL_COUNT)
                );
    }

    private boolean isExistsByEventId(Long eventId) {
        Long rowCount = namedParameterJdbcTemplate.queryForObject(SELECT_AMOUNT_BY_EVENT_ID_SQL,
                new MapSqlParameterSource(EVENT_ID, eventId), Long.class);
        return rowCount > 0;
    }
}
