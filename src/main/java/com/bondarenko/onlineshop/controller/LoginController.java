package com.bondarenko.onlineshop.controller;

import com.bondarenko.onlineshop.security.SecurityService;
import com.bondarenko.onlineshop.web.util.PageGenerator;
import com.bondarenko.onlineshop.web.util.context.ServiceLocator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {
    private SecurityService securityService =
            (SecurityService) ServiceLocator.getService("securityService");
//    @Autowired
//    private SecurityService securityService;


    @GetMapping("/login")
    protected void getLoginPage(HttpServletResponse response) throws IOException {

        PageGenerator pageGenerator = PageGenerator.getInstance();
        String page = pageGenerator.getPage("login.html");
        response.getWriter().write(page);
    }

    @PostMapping("/login")
    protected void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        String token = securityService.login(login, password);

        if (token != null) {
            response.addCookie(new Cookie("user-token", token));
            response.sendRedirect("/*");
        } else {
            PageGenerator pageGenerator = PageGenerator.getInstance();
            String page = pageGenerator.getPage("login.html");
            response.getWriter().write(page);
            response.getWriter().write("<h3 style=position:absolute;left:33%;>Please enter correct login and password! </h3>");
        }
    }
}
