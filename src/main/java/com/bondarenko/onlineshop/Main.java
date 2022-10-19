//package com.bondarenko.onlineshop;
//
//import com.bondarenko.onlineshop.dao.jdbc.JdbcProductDao;
//import com.bondarenko.onlineshop.dao.jdbc.JdbcUserDao;
//import com.bondarenko.onlineshop.web.servlets.*;
//import jakarta.servlet.DispatcherType;
//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.servlet.FilterHolder;
//import org.eclipse.jetty.servlet.ServletContextHandler;
//import org.eclipse.jetty.servlet.ServletHolder;
//import com.bondarenko.onlineshop.security.SecurityService;
//import com.bondarenko.onlineshop.service.ProductService;
//import com.bondarenko.onlineshop.service.UserService;
//import com.bondarenko.onlineshop.web.security.SecurityFilter;
//
//import java.util.EnumSet;
//
//public class Main {
//    public static void main(String[] args) throws Exception {
//
//        JdbcUserDao jdbcUserDao = new JdbcUserDao();
//        JdbcProductDao jdbcProductDao = new JdbcProductDao();
//
//        UserService userService = new UserService(jdbcUserDao);
//        ProductService productService = new ProductService(jdbcProductDao);
//
//        SecurityService securityService = new SecurityService(userService);
//        SecurityFilter securityFilter = new SecurityFilter(userService);
//
//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//
//        context.addServlet(new ServletHolder(new AllProductsServlet(productService)), "/*");
//        context.addServlet(new ServletHolder(new AllProductsServlet(productService)), "/products");
//        context.addServlet(new ServletHolder(new AddProductServlet(productService)), "/products/add");
//        context.addServlet(new ServletHolder(new DeleteProductServlet(productService)), "/products/delete");
//        context.addServlet(new ServletHolder(new UpdateProductServlet(productService)), "/products/update");
//        context.addServlet(new ServletHolder(new SearchProductServlet(productService)), "/products/search");
//        context.addServlet(new ServletHolder(new LoginServlet(securityService)), "/login");
//        context.addFilter(new FilterHolder(securityFilter), "/*", EnumSet.of(DispatcherType.REQUEST));
//
//        Server server = new Server(8080);
//        server.setHandler(context);
//
//        server.start();
//    }
//}
//
//
