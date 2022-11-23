package com.bondarenko.onlineshop.dao.jdbc.jdbcTemplate.resultSetExucutor;

import com.bondarenko.onlineshop.dao.jdbc.jdbcTemplate.RowMapper;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ResultSetExecutorImpl implements ResultSetExecutor {
    @SneakyThrows
    public ResultSet getResultSet(String sql, Object[] arguments, PreparedStatement preparedStatement) {
        int rowNumber = 1;
        for (Object argument : arguments) {
            preparedStatement.setObject(rowNumber++, argument);
        }
        return preparedStatement.executeQuery();
    }

    @SneakyThrows
    public <T> List<T> getData(RowMapper<T> rowMapper, ResultSet resultSet) {
        List<T> products = new ArrayList<>();
        while (resultSet.next()) {
            T product = rowMapper.mapRow(resultSet);
            products.add(product);
        }
        return products;
    }

    @SneakyThrows
    public int getRowQuantity(PreparedStatement preparedStatement, Object[] arguments) {
        int rowNumber = 1;
        for (Object argument : arguments) {
            preparedStatement.setObject(rowNumber++, argument);
        }
        preparedStatement.executeUpdate();
        return rowNumber;
    }
}