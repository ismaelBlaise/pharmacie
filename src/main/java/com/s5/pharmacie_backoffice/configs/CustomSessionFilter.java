package com.s5.pharmacie_backoffice.configs;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomSessionFilter implements Filter {

    private static final Set<String> AUTHORIZED_URLS = Set.of("/", "/utilisateurs/connexion");
    
    @Override
    public void doFilter(jakarta.servlet.ServletRequest request,
                         jakarta.servlet.ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        String requestURI = httpRequest.getRequestURI();

        
        if (requestURI.startsWith("/assets/") || AUTHORIZED_URLS.contains(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        
        if (session == null) {
            httpResponse.sendRedirect("/");
            return;
        }

        chain.doFilter(request, response);
    }
}
