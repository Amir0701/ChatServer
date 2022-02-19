package com.lambakean.domain.security.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.validityTimeMs}")
    private Long validityTimeMs;

    public String createToken(@NonNull Long userId, @NonNull Long validityTimeMs) {

        // todo handle the situation when the secret is null

        Claims claims = Jwts.claims();

        LocalDateTime expiresAt = LocalDateTime.now().plus(validityTimeMs, ChronoUnit.MILLIS);

        claims.put("userId", userId);
        claims.put("expiresAt", expiresAt);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Long getUserId(@NonNull String token) {

        // todo handle the situation when the secret is null

        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

        return (Long) claims.getBody().get("userId");
    }
}
