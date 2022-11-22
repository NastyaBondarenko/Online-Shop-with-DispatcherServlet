package com.bondarenko.onlineshop.dao.jdbc;

import com.bondarenko.onlineshop.dao.UserDao;
import com.bondarenko.onlineshop.dao.jdbc.jdbcTemplate.JdbcTemplate;
import com.bondarenko.onlineshop.dao.jdbc.mapper.UserRowMapper;
import com.bondarenko.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcUserDao implements UserDao {
    private static final String FIND_USER_SQL = "SELECT id, login, password, salt FROM users WHERE login=?";
    private final UserRowMapper userRowMapper = new UserRowMapper();
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> findUser(String login) {
        return Optional.ofNullable((User) jdbcTemplate.queryObject(FIND_USER_SQL, userRowMapper, login));
    }
}





