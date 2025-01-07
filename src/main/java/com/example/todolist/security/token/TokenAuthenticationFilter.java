package com.example.todolist.security.token;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class TokenAuthenticationFilter extends GenericFilterBean {

    private final TokenService tokenService;
    private static final String BEARER = "Bearer ";

    public TokenAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String requestURI = httpRequest.getRequestURI();
        logger.info("Request URI: " + requestURI);

        if (requestURI.equals("/api/auth/authorization/todo-list") || requestURI.equals("/api/users/create")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Optional<String> token = Optional.ofNullable(httpRequest.getHeader("Authorization"))
                .filter(s -> s.length() > BEARER.length() && s.startsWith(BEARER))
                .map(s -> s.substring(BEARER.length()));

        if (token.isEmpty()) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Authorization token is missing");
            return;
        }

        Optional<Authentication> authentication = tokenService.verifyToken(token);

        if (authentication.isPresent()) {
            SecurityContextHolder.getContext().setAuthentication(authentication.get());
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Token is invalid");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }
}
