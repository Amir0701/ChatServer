package com.lambakean.domain.security.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    public String createToken(@NonNull Long userId, @NonNull Long validityTimeMs) {

        Claims claims = Jwts.claims();

        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + validityTimeMs);

        claims.put("userId", userId);
        claims.put("expiresAt", expiresAt);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Long getUserId(@NonNull String token) {

        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

        return (Long) claims.getBody().get("userId");
    }

    public LocalDateTime getExpirationDate(@NonNull String token) {

        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

        Date expiresAt = (Date) claims.getBody().get("expiresAt");

        return LocalDateTime.ofInstant(expiresAt.toInstant(), ZoneId.systemDefault());
    }

    public boolean isExpired(@NonNull String token) {
        return LocalDateTime.now().isBefore(getExpirationDate(token));
    }
}
