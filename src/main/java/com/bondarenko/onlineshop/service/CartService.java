package com.bondarenko.onlineshop.service;

import com.bondarenko.onlineshop.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CartService {
    private final Map<Principal, List<Product>> mapUserToCart = new ConcurrentHashMap<>();
    private final ProductService productService;

    public List<Product> getCart(Principal principal) {
        return mapUserToCart.get(principal);
    }

    public void addToCart(int id, Principal principal) {
        Product product = productService.findById(id).get();
        List<Product> cart = getProducts(principal);
        cart.add(product);
    }

    public void deleteFromCart(int id, Principal principal) {
        List<Product> cart = getProducts(principal);
        cart.removeIf(product -> Objects.equals(product.getId(), id));
    }

    private List<Product> getProducts(Principal principal) {
        mapUserToCart.computeIfAbsent(principal, k -> new ArrayList<>());
        return mapUserToCart.get(principal);
    }
}