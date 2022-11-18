package com.bondarenko.onlineshop.web.controller;

import com.bondarenko.onlineshop.entity.Product;
import com.bondarenko.onlineshop.service.CartService;
import com.bondarenko.onlineshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller

public class ProductController {
    private final ProductService productService;
    private final CartService cartService;

    public ProductController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping({"/products", "/*"})
    protected String getAll(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "allproducts";
    }

    @GetMapping("/products/add")
    protected String getAddProductPage() {
        return "add_product";
    }

    @PostMapping("/products/add")
    protected String add(@ModelAttribute Product product) {
        productService.add(product);
        return "redirect:/products";
    }

    @GetMapping("/products/search")
    protected String search(@RequestParam String searchText, Model model) {
        List<Product> products = productService.search(searchText);
        model.addAttribute("products", products);
        return "allproducts";
    }

    @GetMapping("/products/delete")
    protected String getDeleteProductPage() {
        return "deleteproduct";
    }

    @PostMapping("/products/delete")
    protected String delete(@RequestParam int id) {
        productService.delete(id);
        return "redirect:/products";
    }

    @GetMapping("/products/update")
    protected String getUpdateProductPage() {
        return "updateproduct";
    }

    @PostMapping("/products/update")
    protected String update(@ModelAttribute Product product) {
        productService.update(product);
        return "redirect:/products";
    }

    @GetMapping({"/products/cart"})
    protected String getAllFromCart(Model model) {
        List<Product> cart = cartService.getCart();
        model.addAttribute("cart", cart);
        return "product_cart";
    }

    @PostMapping("/products/cart/{id}")
    protected String addToCart(@RequestParam int id) {
        cartService.addToCart(id);
        return "redirect:/products";
    }

    @PostMapping("/products/cart/delete")
    protected String deleteFromCart(@RequestParam int id) {
        cartService.deleteFromCart(id);
        return "redirect:/products/cart";
    }
}