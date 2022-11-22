package com.bondarenko.onlineshop.service;

import com.bondarenko.onlineshop.dao.ProductDao;
import com.bondarenko.onlineshop.entity.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void add(Product product) {
        LocalDateTime now = LocalDateTime.now();
        product.setCreationDate(now);
        productDao.add(product);
    }

    public void delete(int id) {
        productDao.delete(id);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public void update(Product product) {
        productDao.update(product);
    }

    public List<Product> search(String searchText) {
        return productDao.search(searchText);
    }

    public Optional<Product> findById(int id) {
        return productDao.findById(id);
    }
}