package service;

import dao.ProductDao;
import entity.Product;

import java.time.LocalDateTime;
import java.util.List;

public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

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
