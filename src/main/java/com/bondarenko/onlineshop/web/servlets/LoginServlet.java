package com.bondarenko.onlineshop.web.servlets;

import com.bondarenko.onlineshop.web.util.GenericApplicationContext;
import com.bondarenko.onlineshop.security.SecurityService;
import com.bondarenko.onlineshop.web.util.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private SecurityService securityService =
            (SecurityService) GenericApplicationContext.getService("securityService");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.getInstance();
        String page = pageGenerator.getPage("login.html");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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


