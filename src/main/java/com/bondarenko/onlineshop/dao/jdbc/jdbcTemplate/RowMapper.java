package com.bondarenko.onlineshop.dao.jdbc.jdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {
    T mapRow(ResultSet resultSet) throws SQLException;
}