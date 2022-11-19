package com.bondarenko.onlineshop.web.security;

import com.bondarenko.onlineshop.security.SecurityService;
import com.bondarenko.onlineshop.security.Session;
import com.bondarenko.onlineshop.security.CurrentUser;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Setter
@Slf4j
public class SecurityFilter implements Filter {
    private final List<String> allowedPath = List.of("/login");
    private SecurityService securityService;

    @Override
    public void init(FilterConfig filterConfig) {
        WebApplicationContext applicationContext = (WebApplicationContext)
                filterConfig.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        securityService = (SecurityService) applicationContext.getBean("securityService");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String requestURI = httpServletRequest.getRequestURI();
        for (String allowedPath : allowedPath) {
            if (requestURI.startsWith(allowedPath)) {
                chain.doFilter(request, response);
                return;
            }
        }
        Optional<String> userTokenOptional = getUserToken(httpServletRequest);
        if (userTokenOptional.isEmpty()) {
            httpServletResponse.sendRedirect("/login");
            return;
        }
        String userToken = userTokenOptional.get();
        Optional<Session> sessionOptional = securityService.getSession(userToken);

        if (sessionOptional.isEmpty()) {
            httpServletResponse.sendRedirect("/login");
            return;
        }
        Session session = sessionOptional.get();
        request.setAttribute("session", session);
        CurrentUser.setCurrentUser(session.getUser());

        log.info("User is authorized");
        try {
            chain.doFilter(request, response);
        } finally {
            CurrentUser.clear();
        }
    }

    private Optional<String> getUserToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (("user-token").equals(cookie.getName())) {
                    return Optional.of(cookie.getValue());
                }
            }
        }
        return Optional.empty();
    }
}


