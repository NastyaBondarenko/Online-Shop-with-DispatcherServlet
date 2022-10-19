package com.bondarenko.onlineshop;

import com.bondarenko.onlineshop.dao.jdbc.JdbcProductDao;
import com.bondarenko.onlineshop.dao.jdbc.JdbcUserDao;
import com.bondarenko.onlineshop.security.SecurityService;
import com.bondarenko.onlineshop.service.ProductService;
import com.bondarenko.onlineshop.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static final Map<String, Object> REGISTRY = new HashMap<>();

    static {

        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        JdbcProductDao jdbcProductDao = new JdbcProductDao();

        UserService userService = new UserService(jdbcUserDao);
        ProductService productService = new ProductService(jdbcProductDao);
        SecurityService securityService = new SecurityService(userService);

        REGISTRY.put("userService", userService);
        REGISTRY.put("productService", productService);
        REGISTRY.put("securityService", securityService);
    }

    public static Object getService(String serviceName) {
        return REGISTRY.get(serviceName);
    }
}
