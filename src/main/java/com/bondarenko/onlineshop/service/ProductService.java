package com.bondarenko.onlineshop.service;

import com.bondarenko.onlineshop.dao.jdbc.repository.ProductRepository;
import com.bondarenko.onlineshop.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void add(Product product) {
        LocalDateTime now = LocalDateTime.now();
        product.setCreationDate(now);
        productRepository.add(product);
    }

    public void delete(int id) {
        productRepository.delete(id);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void update(Product product) {
        productRepository.update(product);
    }

    public List<Product> search(String searchText) {
        return productRepository.search(searchText);
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }
}