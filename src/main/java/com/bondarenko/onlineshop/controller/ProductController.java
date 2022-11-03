package com.bondarenko.onlineshop.controller;

import com.bondarenko.onlineshop.entity.Product;
import com.bondarenko.onlineshop.service.ProductService;
import com.bondarenko.onlineshop.web.util.context.ServiceLocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
public class ProductController {

    private ProductService productService =
            (ProductService) ServiceLocator.getService("productService");

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

    @PostMapping("/products/add")// replace
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
    protected String update(HttpServletRequest request, HttpServletResponse response) {

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        LocalDateTime creation_date = LocalDateTime.parse(request.getParameter("creation_date"));

        Product product = new Product(id, name, price, creation_date);
        productService.update(product);
        return "redirect:/products";
    }
}

//    @PostMapping("/products/update")
//    protected String update(@ModelAttribute Product product) {
//        productService.update(product);
//        return "redirect:/products";
//    }

