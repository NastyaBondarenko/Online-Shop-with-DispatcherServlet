package com.bondarenko.onlineshop.web.security;

import com.bondarenko.onlineshop.security.SecurityService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Setter
@Slf4j
public class SecurityFilter implements Filter {

    private SecurityService securityService;
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
        WebApplicationContext applicationContext = (WebApplicationContext)
                request.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        securityService = (SecurityService) applicationContext.getBean("securityService");

        log.info("Check if user is authorized");
        if (securityService.isAuth(httpServletRequest.getCookies())) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {
    }
}


