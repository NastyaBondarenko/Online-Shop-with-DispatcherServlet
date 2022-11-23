package com.bondarenko.onlineshop.dao.jdbc.jdbcTemplate;


import com.bondarenko.onlineshop.dao.jdbc.jdbcTemplate.resultSetExucutor.ResultSetExecutorImpl;
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
    public List<T> query(String sql, RowMapper<T> rowMapper, T... arguments) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = resultSetExecutor.getResultSet(preparedStatement, arguments);
            return resultSetExecutor.getData(rowMapper, resultSet);
        }
    }

    @SneakyThrows
    public T queryObject(String sql, RowMapper<T> rowMapper, T... arguments) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = resultSetExecutor.getResultSet(preparedStatement, arguments);
            return resultSetExecutor.getData(rowMapper, resultSet).get(0);
        }
    }

    @SneakyThrows
    public int executeUpdate(String sql, T... arguments) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return resultSetExecutor.getRowQuantity(preparedStatement, arguments);
        }
    }
}