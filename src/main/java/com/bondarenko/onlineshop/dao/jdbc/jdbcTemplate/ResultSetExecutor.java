package com.bondarenko.onlineshop.dao.jdbc.jdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public interface ResultSetExecutor {
    ResultSet getResultSet(String sql, Object[] args, PreparedStatement preparedStatement);

    <T> List<T> getData(RowMapper<T> rowMapper, ResultSet resultSet);

    int getRowNumber(PreparedStatement preparedStatement, Object[] arguments);
}