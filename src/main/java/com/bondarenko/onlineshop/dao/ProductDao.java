package com.bondarenko.onlineshop.dao;

import com.bondarenko.onlineshop.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findAll();

    void add(Product product);

    void delete(int id);

    void update(Product product);

    List<Product> search(String searchText);

    Product getById(int id);
}
