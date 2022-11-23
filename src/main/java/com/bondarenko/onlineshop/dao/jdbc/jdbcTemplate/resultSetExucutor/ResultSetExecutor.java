package com.bondarenko.onlineshop.dao.jdbc.jdbcTemplate.resultSetExucutor;

import com.bondarenko.onlineshop.dao.jdbc.jdbcTemplate.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public interface ResultSetExecutor {
    <T> ResultSet getResultSet(PreparedStatement preparedStatement, T... arguments);

    <T> List<T> getData(RowMapper<T> rowMapper, ResultSet resultSet);

    <T> int getRowQuantity(PreparedStatement preparedStatement, T... arguments);
}