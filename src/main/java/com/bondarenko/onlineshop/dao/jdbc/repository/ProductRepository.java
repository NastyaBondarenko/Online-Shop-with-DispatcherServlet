package com.bondarenko.onlineshop.dao.jdbc.repository;

import com.bondarenko.onlineshop.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    void add(Product product);

    void delete(int id);

    void update(Product product);

    List<Product> search(String searchText);

    Optional<Product> findById(int id);
}
