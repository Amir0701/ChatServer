package com.lambakean.domain.security.authentication;

import com.lambakean.data.model.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private String token;
    private User user;

    public JwtAuthenticationToken(String token, User user) {
        super(Collections.emptySet());
        this.token = token;
        this.user = user;
    }

    public JwtAuthenticationToken(String token) {
        super(Collections.emptySet());
        this.token = token;
    }

    public JwtAuthenticationToken() {
        super(Collections.emptySet());
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public User getPrincipal() {
        return user;
    }
}