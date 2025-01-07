package com.example.todolist.security.token;

import java.util.List;
import java.util.Optional;

import com.example.todolist.security.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class TokenService {


    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public TokenService(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    public Optional<Authentication> verifyToken(Optional<String> token) {
        if (token.isPresent() && jwtUtil.validateToken(token.get())) {
            String username = jwtUtil.extractUsername(token.get());
            String role = jwtUtil.extractRoles(token.get());

            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails != null) {
                return Optional.of(new UsernamePasswordAuthenticationToken(userDetails, null, authorities));
            }
        }
        return Optional.empty();
    }

}