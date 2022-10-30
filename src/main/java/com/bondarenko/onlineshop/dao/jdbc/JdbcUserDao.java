package com.bondarenko.onlineshop.dao.jdbc;

import com.bondarenko.onlineshop.dao.UserDao;
import com.bondarenko.onlineshop.dao.jdbc.mapper.UserRowMapper;
import com.bondarenko.onlineshop.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Setter
@Getter
public class JdbcUserDao implements UserDao {
    private ConnectionFactory connectionFactory;
    private UserRowMapper userRowMapper;

    private static final String FIND_USER_SQL = "SELECT id, login, password, salt FROM users WHERE login=?";

    @Override
    public User findUser(String login) {
        try (Connection connection = connectionFactory.connectionToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_SQL)) {

            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    return userRowMapper.mapRow(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Can not search user with : " + login, e);
        }
    }
}







