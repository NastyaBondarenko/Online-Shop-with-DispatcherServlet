package com.bondarenko.onlineshop.web.servlets;

import com.bondarenko.onlineshop.web.util.GenericApplicationContext;
import com.bondarenko.onlineshop.entity.Product;
import com.bondarenko.onlineshop.service.ProductService;
import com.bondarenko.onlineshop.web.util.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllProductsServlet extends HttpServlet {
    private ProductService productService =
            (ProductService) GenericApplicationContext.getService("productService");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> paramMap = new HashMap<>();
        List<Product> products;

        try {
            products = productService.findAll();
            paramMap.put("products", products);
            PageGenerator pageGenerator = PageGenerator.getInstance();
            String page = pageGenerator.getPage("allproducts.html", paramMap);

            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(page);
        } catch (IOException exception) {
            throw new RuntimeException("Can not get all products from database", exception);
        }
    }
}
