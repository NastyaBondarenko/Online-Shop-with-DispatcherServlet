//package com.bondarenko.onlineshop.web.servlets;
//
//import com.bondarenko.onlineshop.web.util.context.ServiceLocator;
//import com.bondarenko.onlineshop.entity.Product;
//import com.bondarenko.onlineshop.service.ProductService;
//import com.bondarenko.onlineshop.web.util.PageGenerator;
//
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Map;
//
//public class AddProductServlet extends HttpServlet {
//    private ProductService productService =
//            (ProductService) ServiceLocator.getService("productService");
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        PageGenerator pageGenerator = PageGenerator.getInstance();
//        String page = pageGenerator.getPage("add_product.html");
//        response.getWriter().write(page);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        try {
//            Product product = getProductFromRequest(request);
//            productService.add(product);
//            response.sendRedirect("/products");
//
//        } catch (IOException e) {
//            String errorMessage = "Product has not been added. Check and enter correct data in the fields";
//            PageGenerator pageGenerator = PageGenerator.getInstance();
//
//            Map<String, Object> parameters = Map.of("errorMessage", errorMessage);
//            String page = pageGenerator.getPage("add_product.html", parameters);
//            response.getWriter().write(page);
//        }
//    }
//
//    private Product getProductFromRequest(HttpServletRequest request) {
//        return Product.builder().
//                name(request.getParameter("name"))
//                .price(Double.parseDouble(request.getParameter("price")))
//                .build();
//    }
//}
//
//
//
