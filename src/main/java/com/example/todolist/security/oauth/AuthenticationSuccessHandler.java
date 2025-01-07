package com.example.todolist.security.oauth;

import com.example.todolist.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        org.springframework.security.core.Authentication authentication) throws IOException {
        handle(request, response, authentication);
        super.clearAuthenticationAttributes(request);
    }

    @Override
    protected void handle(HttpServletRequest request,
                          HttpServletResponse response,
                          org.springframework.security.core.Authentication authentication) throws IOException {

        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(roleStr -> roleStr.replace("ROLE_", ""))
                .findFirst()
                .orElse("USER");

        // Generate the JWT token
        String token = jwtUtil.generateToken(authentication.getName(), role);

        // Set the response content type to JSON
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        // Write the token to the response body
        response.getWriter().write("{\"token\": \"" + token + "\"}");
        response.getWriter().flush();
    }
}
