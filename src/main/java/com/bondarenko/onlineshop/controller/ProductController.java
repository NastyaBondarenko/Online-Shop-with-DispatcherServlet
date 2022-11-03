package com.bondarenko.onlineshop.controller;

import com.bondarenko.onlineshop.entity.Product;
import com.bondarenko.onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

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
    protected String add(@RequestParam String name, @RequestParam double price) {
        Product product = Product.builder().
                name(name)
                .price(price)
                .build();

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
    protected String update(@ModelAttribute Product product, @RequestParam String creation_date) {
        LocalDateTime date = LocalDateTime.parse(creation_date);
        Product newProduct = new Product(product.getId(), product.getName(), product.getPrice(), date);
        productService.update(newProduct);
        return "redirect:/products";
    }
}



