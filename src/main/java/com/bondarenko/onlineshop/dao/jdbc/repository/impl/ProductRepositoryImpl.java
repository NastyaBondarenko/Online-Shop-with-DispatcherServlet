package com.bondarenko.onlineshop.dao.jdbc.repository.impl;

import com.bondarenko.onlineshop.dao.jdbc.repository.ProductRepository;
import com.bondarenko.onlineshop.dao.jdbc.jdbcTemplate.JdbcTemplate;
import com.bondarenko.onlineshop.dao.jdbc.mapper.ProductRowMapper;
import com.bondarenko.onlineshop.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private static final String FIND_ALL_SQL = "SELECT id, name, price, creation_date FROM products;";
    private static final String ADD_SQL = "INSERT INTO products (name, price, creation_date) VALUES (?,?,?);";
    private static final String DELETE_SQL = "DELETE FROM products WHERE id=?;";
    private static final String UPDATE_SQL = "UPDATE products SET name=?, price=? WHERE id=?;";
    private static final String SEARCH_SQL = "SELECT id, name, price, creation_date FROM products WHERE  (name) LIKE ?;";
    private static final String GET_BY_ID_SQL = "SELECT id, name, price, creation_date FROM products WHERE id=?;";
    private final ProductRowMapper productRowMapper = new ProductRowMapper();
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, productRowMapper);
    }

    @Override
    public void add(Product product) {
        jdbcTemplate.executeUpdate(ADD_SQL, product.getName(), product.getPrice(), product.getCreationDate());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public void update(Product product) {
        jdbcTemplate.executeUpdate(UPDATE_SQL, product.getName(), product.getPrice(), product.getId());
    }

    @Override
    @SneakyThrows
    public Optional<Product> findById(int id) {
        return Optional.ofNullable((Product) jdbcTemplate.queryObject(GET_BY_ID_SQL, productRowMapper, id));
    }

    @Override
    public List<Product> search(String searchText) {
        return jdbcTemplate.query(SEARCH_SQL, productRowMapper, searchText);
    }
}