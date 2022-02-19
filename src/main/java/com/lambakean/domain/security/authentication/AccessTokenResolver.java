package com.lambakean.domain.security.authentication;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class AccessTokenResolver {

    public Optional<String> resolveToken(@NonNull HttpServletRequest httpServletRequest) {

        String bearerToken = httpServletRequest.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return Optional.of(bearerToken.substring(7));
        }

        return Optional.empty();
    }
}
