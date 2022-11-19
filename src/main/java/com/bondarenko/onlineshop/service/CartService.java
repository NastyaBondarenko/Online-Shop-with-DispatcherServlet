package com.bondarenko.onlineshop.service;

import com.bondarenko.onlineshop.entity.Product;
import com.bondarenko.onlineshop.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CartService {
    private final Map<User, List<Product>> userToCartMap = new ConcurrentHashMap<>();
    private ProductService productService;

    public CartService(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> getCart() {

        User currentUser = CurrentUser.getCurrentUser();
        List<Product> cart = userToCartMap.get(currentUser);
        return cart;
    }

    public void addToCart(int id) {
        User currentUser = CurrentUser.getCurrentUser();
        Product product = productService.findById(id);
        List<Product> cart;

        if (userToCartMap.containsKey(currentUser)) {
            cart = userToCartMap.get(currentUser);
        } else {
            cart = new ArrayList<>();
            userToCartMap.put(currentUser, cart);
        }
        cart.add(product);
    }

    public void deleteFromCart(int id) {
        User currentUser = CurrentUser.getCurrentUser();
        List<Product> products = userToCartMap.get(currentUser);
        Product product = productService.findById(id);
        products.remove(product);
    }
}