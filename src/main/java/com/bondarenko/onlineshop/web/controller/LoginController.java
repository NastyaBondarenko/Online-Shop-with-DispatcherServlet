package com.bondarenko.onlineshop.web.controller;

import com.bondarenko.onlineshop.dto.Credentials;
import com.bondarenko.onlineshop.dto.SessionData;
import com.bondarenko.onlineshop.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final SecurityService securityService;

    @GetMapping("/login")
    protected String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    protected String login(@RequestParam String login, @RequestParam String password, HttpServletResponse response) {

        Credentials credentials = new Credentials(login, password);
        Optional<SessionData> sessionDataOptional = securityService.login(credentials);

        if (sessionDataOptional.isEmpty()) {
            return "login";
        }
        SessionData sessionData = sessionDataOptional.get();

        Cookie cookie = new Cookie("user-token", sessionData.getToken());
        int sessionTime = sessionData.getSessionTime();
        cookie.setMaxAge(sessionTime);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
