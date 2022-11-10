package com.bondarenko.onlineshop.web.controller;

import com.bondarenko.onlineshop.entity.SessionData;
import com.bondarenko.onlineshop.security.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    private final SecurityService securityService;

    public LoginController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/login")
    protected String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    protected String login(@RequestParam String login, @RequestParam String password, HttpServletResponse response) {
        SessionData sessionData = securityService.login(login, password);
        if (sessionData != null) {
            Cookie cookie = new Cookie("user-token", sessionData.getToken());
            int sessionTime = sessionData.getSessionTime();
            cookie.setMaxAge(sessionTime);
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            return "login";
        }
    }
}
