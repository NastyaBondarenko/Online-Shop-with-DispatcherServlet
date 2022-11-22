package com.bondarenko.onlineshop.dao.jdbc.jdbcTemplate;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@RequiredArgsConstructor
public class JdbcTemplate<T> {
    private final DataSource dataSource;
    private final ResultSetExecutorImpl resultSetExecutor = new ResultSetExecutorImpl();

    @SneakyThrows
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... arguments) {
        PreparedStatement preparedStatement = getPreparedStatement(sql);
        ResultSet resultSet = resultSetExecutor.getResultSet(sql, arguments, preparedStatement);
        return resultSetExecutor.getData(rowMapper, resultSet);
    }

    @SneakyThrows
    public T queryObject(String sql, RowMapper<T> rowMapper, T... arguments) {
        PreparedStatement preparedStatement = getPreparedStatement(sql);
        ResultSet resultSet = resultSetExecutor.getResultSet(sql, arguments, preparedStatement);
        return resultSetExecutor.getData(rowMapper, resultSet).get(0);
    }

    @SneakyThrows
    public int executeUpdate(String sql, Object... arguments) {
        PreparedStatement preparedStatement = getPreparedStatement(sql);
        return resultSetExecutor.getRowNumber(preparedStatement, arguments);
    }

    @SneakyThrows
    private PreparedStatement getPreparedStatement(String sql) {
        Connection connection = dataSource.getConnection();
        return connection.prepareStatement(sql);
    }
}