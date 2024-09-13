package com.example.todolist.security.user.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Map;

@Data
public class CustomUserDetails implements UserDetails {

    private String name;
    private String username;

    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    private final String password = "NA";

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


