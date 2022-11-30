package com.bondarenko.onlineshop.web.controller;

import com.bondarenko.onlineshop.entity.Product;
import com.bondarenko.onlineshop.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
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

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"/products", "/*"})
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    protected String getAll(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "allproducts";
    }

    @GetMapping("/products/add")
    @PreAuthorize("hasRole('ADMIN')")
    protected String getAddProductPage() {
        return "add_product";
    }

    @PostMapping("/products/add")
    @PreAuthorize("hasRole('ADMIN')")
    protected String add(@ModelAttribute Product product) {
        productService.add(product);
        return "redirect:/products";
    }

    @GetMapping("/products/delete")
    @PreAuthorize("hasRole('ADMIN')")
    protected String getDeleteProductPage() {
        return "deleteproduct";
    }

    @PostMapping("/products/delete")
    @PreAuthorize("hasRole('ADMIN')")
    protected String delete(@RequestParam int id) {
        productService.delete(id);
        return "redirect:/products";
    }

    @GetMapping("/products/update")
    @PreAuthorize("hasAuthority('admin:update')")
    protected String getUpdateProductPage() {
        return "updateproduct";
    }

    @PostMapping("/products/update")
    @PreAuthorize("hasAuthority('admin:update')")
    protected String update(@ModelAttribute Product product) {
        productService.update(product);
        return "redirect:/products";
    }

    @GetMapping("/products/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    protected String search(@RequestParam String searchText, Model model) {
        List<Product> products = productService.search(searchText);
        model.addAttribute("products", products);
        return "allproducts";
    }
}