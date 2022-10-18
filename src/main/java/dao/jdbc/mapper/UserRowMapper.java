package dao.jdbc.mapper;

import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        String salt = resultSet.getString("salt");

        return User.builder().
                id(id)
                .login(login)
                .password(password)
                .salt(salt)
                .build();
    }
}
