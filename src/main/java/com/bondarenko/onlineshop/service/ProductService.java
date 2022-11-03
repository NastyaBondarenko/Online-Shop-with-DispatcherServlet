package com.bondarenko.onlineshop.service;

import com.bondarenko.onlineshop.dao.ProductDao;
import com.bondarenko.onlineshop.entity.Product;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
public class ProductService {
    private ProductDao productDao;

    public void add(Product product) {
        LocalDateTime now = LocalDateTime.now();
        product.setCreationDate(now);
        productDao.add(product);
        System.out.println("Product added");
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
}
