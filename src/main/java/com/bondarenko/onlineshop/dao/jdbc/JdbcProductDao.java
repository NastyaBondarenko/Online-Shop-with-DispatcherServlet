package com.bondarenko.onlineshop.dao.jdbc;

import com.bondarenko.onlineshop.dao.ProductDao;
import com.bondarenko.onlineshop.dao.jdbc.mapper.ProductRowMapper;
import com.bondarenko.onlineshop.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class JdbcProductDao implements ProductDao {
    private static final String FIND_ALL_SQL = "SELECT id, name, price, creation_date FROM products;";
    private static final String ADD_SQL = "INSERT INTO products (name, price, creation_date) VALUES (?,?,?);";
    private static final String DELETE_SQL = "DELETE FROM products WHERE id=?;";
    private static final String UPDATE_SQL = "UPDATE products SET name=?, price=?, creation_date=? WHERE id=?;";
    private static final String SEARCH_SQL = "SELECT id, name, price, creation_date FROM products WHERE  (name) LIKE ?;";

    private final ProductRowMapper productRowMapper = new ProductRowMapper();
    private final DataSource dataSource;


    @Override
    public List<Product> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL)) {
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = productRowMapper.mapRow(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to get products from database", exception);
        }
    }

    @Override
    public void add(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_SQL)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(product.getCreationDate()));
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            throw new RuntimeException("Can not add product to database", exception);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            preparedStatement.setInt(1, Integer.parseInt(String.valueOf(id)));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can not delete product from database", e);
        }
    }

    @Override
    public void update(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setLong(4, product.getId());
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(product.getCreationDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can not edit product from database", e);
        }
    }

    @Override
    public List<Product> search(String searchText) {
        String searchWord = "%" + searchText + "%";
        List<Product> products = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_SQL)) {

            preparedStatement.setString(1, searchWord);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Product product = productRowMapper.mapRow(resultSet);
                    products.add(product);
                }
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Can not search product with text: " + searchText, e);
        }
    }
}