package com.lambakean.domain.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final AccessTokenResolver accessTokenResolver;

    @Autowired
    public JwtAuthenticationFilter(JwtAuthenticationProvider jwtAuthenticationProvider,
                                   AccessTokenResolver accessTokenResolver) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.accessTokenResolver = accessTokenResolver;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        Optional<String> accessTokenOptional = accessTokenResolver.resolveToken((HttpServletRequest) servletRequest);

        if(accessTokenOptional.isPresent()) {

            String accessToken = accessTokenOptional.get();

            try {
                Authentication authentication = jwtAuthenticationProvider.authenticate(
                        new JwtAuthenticationToken(accessToken)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (AuthenticationException ignored) {}

        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
