package com.bondarenko.onlineshop.controller;

import com.bondarenko.onlineshop.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    private SecurityService securityService;

    @GetMapping("/login")
    protected String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    protected String login(@RequestParam String login, @RequestParam String password, HttpServletResponse response) {
        String token = securityService.login(login, password);
        if (token != null) {
            response.addCookie(new Cookie("user-token", token));
            return "redirect:/*";
        } else {
            return "login";
        }
    }
}
