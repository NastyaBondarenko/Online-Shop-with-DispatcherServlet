package com.bondarenko.onlineshop.dao.jdbc.jdbcTemplate.resultSetExucutor;

import com.bondarenko.onlineshop.dao.jdbc.jdbcTemplate.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public interface ResultSetExecutor {
    ResultSet getResultSet(PreparedStatement preparedStatement, Object[] arguments);

    <T> List<T> getData(RowMapper<T> rowMapper, ResultSet resultSet);

    int getRowQuantity(PreparedStatement preparedStatement, Object[] arguments);
}