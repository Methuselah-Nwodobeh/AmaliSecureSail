package com.amalitech.securesail.amalisecuresail.configurations.security;

import com.amalitech.securesail.amalisecuresail.global.context_holders.HttpRequestContextHolder;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpRequestContextFilter implements Filter {
    /*
     * This is a filter to get the request object and set it in the request context holder
     */

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest httpServletRequest) {
            HttpRequestContextHolder.setRequest(httpServletRequest);
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            HttpRequestContextHolder.clear();
        }
    }

}