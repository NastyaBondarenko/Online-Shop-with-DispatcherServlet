package web.servlets;

import entity.Product;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.util.PageGenerator;
import service.ProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchProductServlet extends HttpServlet {
    private final ProductService productService;

    public SearchProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Product> products = productService.search(request.getParameter("searchText"));
        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("products", products);
        try {
            response.setContentType("text/html; charset=utf-8");
            response.getWriter().println(PageGenerator.getInstance().getPage("allproducts.html", parametersMap));
        } catch (IOException exception) {
            throw new RuntimeException("Cant find product from database");
        }
    }
}




