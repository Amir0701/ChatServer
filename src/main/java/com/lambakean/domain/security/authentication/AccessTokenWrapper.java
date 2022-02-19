package com.lambakean.domain.security.authentication;

import com.lambakean.data.model.User;

import java.time.LocalDateTime;

public class AccessTokenWrapper {

    private String token;

    private User user;
    private LocalDateTime expiresAt;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
