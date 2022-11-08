package com.bondarenko.onlineshop.web.security;

import com.bondarenko.onlineshop.service.UserService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Setter
@Slf4j
public class SecurityFilter implements Filter {
    private UserService userService;
    private final List<String> allowedPath = List.of("/login");

    @Override
    public void init(FilterConfig filterConfig) {
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
        log.info("Check if user is authorized");
        if (userService.isAuth(httpServletRequest.getCookies())) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {
    }
}


