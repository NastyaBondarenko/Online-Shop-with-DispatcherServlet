package web.servlets;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.util.PageGenerator;
import security.SecurityService;

import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private final SecurityService securityService;

    public LoginServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

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


