package com.bondarenko.onlineshop.dao.jdbc.jdbcTemplate;


import com.bondarenko.onlineshop.entity.Product;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate<T> {
    private DataSource dataSource;
    public Product product;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @SneakyThrows
    public List<T> query(String sql, RowMapper<T> rowMapper, Object... args) {
        List<T> products = new ArrayList<>();

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int row = 1;
        for (Object arg : args) {
            preparedStatement.setObject(row, arg);
            row++;
        }
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            T product = rowMapper.mapRow(resultSet);
            products.add(product);

        }
        return products;

    }

    @SneakyThrows
    public int executeUpdate(String sql, Object... args) {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        int row = 1;
        for (Object arg : args) {
            preparedStatement.setObject(row, arg);
            row++;
        }
        preparedStatement.executeUpdate();
        return row;
    }


    @SneakyThrows
    public T queryObject(String sql, RowMapper<T> rowMapper, Object... args) {// [] objects
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        int row = 1;
        for (Object arg : args) {
            preparedStatement.setObject(row, arg);
            row++;
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            throw new RuntimeException("Can't get product with : " + args);
        }
        return rowMapper.mapRow(resultSet);
    }
}