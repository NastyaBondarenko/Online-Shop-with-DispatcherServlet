package com.bondarenko.onlineshop.dao.jdbc.mapper;

import com.bondarenko.onlineshop.entity.User;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        String salt = resultSet.getString("salt");

        return User.builder().
                id(id)
                .user(login)
                .password(password)
                .salt(salt)
                .build();
    }
}
