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

import com.epam.spring.movieTheaterManagement.configuration.DBConfiguration;
import com.epam.spring.movieTheaterManagement.domain.User;
import com.epam.spring.movieTheaterManagement.utils.LocalDateTimeConverter;

@Repository("userDao")
public class UserDao implements Dao<User> {
    private static final String USER_ID = "id";
    private static final String USER_FIRSTNAME = "firstName";
    private static final String USER_LASTNAME = "lastName";
    private static final String USER_EMAIL = "email";
    private static final String USER_BIRTHDAY = "birthday";
    private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM t_user";
    private static final String SELECT_USER_BY_ID_SQL = "SELECT * FROM t_user WHERE id=:id";
    private static final String SELECT_USER_BY_EMAIL_SQL = "SELECT * FROM t_user WHERE email=:email";
    private static final String SAVE_USER_SQL = "INSERT into t_user (firstName, lastName, email, birthday) " +
            "VALUES (:firstName, :lastName, :email, :birthday)";
    private static final String DELETE_USER_SQL = "DELETE FROM t_user WHERE id=:id";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<User> getAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_USERS_SQL, userMapRow());
    }

    public User save(@Nonnull User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put(USER_FIRSTNAME, user.getFirstName());
        paramMap.put(USER_LASTNAME, user.getLastName());
        paramMap.put(USER_EMAIL, user.getEmail());
        paramMap.put(USER_BIRTHDAY, LocalDateTimeConverter.convertToString(user.getBirthday()));
        namedParameterJdbcTemplate.update(SAVE_USER_SQL,
                new MapSqlParameterSource(paramMap), keyHolder);
        user.setId(keyHolder.getKey().longValue());

        return user;
    }

    public void remove(@Nonnull User user) {
        namedParameterJdbcTemplate.update(DELETE_USER_SQL,
                new MapSqlParameterSource(USER_ID, user.getId()));
    }

    public User getById(@Nonnull Long id) {
        return namedParameterJdbcTemplate.queryForObject(SELECT_USER_BY_ID_SQL,
                new MapSqlParameterSource(USER_ID, id), userMapRow());
    }

    public User getUserByEmail(@Nonnull String email) {
        return namedParameterJdbcTemplate.queryForObject(SELECT_USER_BY_EMAIL_SQL,
                new MapSqlParameterSource(USER_EMAIL, email), userMapRow());
    }

    private RowMapper<User> userMapRow() {
        return (rs, rowNum) ->
                new User(
                        rs.getLong(USER_ID),
                        rs.getString(USER_FIRSTNAME),
                        rs.getString(USER_LASTNAME),
                        rs.getString(USER_EMAIL),
                        LocalDateTimeConverter.convertToLocalDateTime(rs.getString(USER_BIRTHDAY))
                );
    }
}
