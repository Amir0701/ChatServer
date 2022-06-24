package com.lambakean.domain.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(request-> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    List<String> s = new ArrayList<String>();
                    s.add("*");
                    List<String> methods = new ArrayList<>();
                    methods.add("GET");
                    methods.add("POST");
                    methods.add("PUT");
                    methods.add("DELETE");
                    methods.add("OPTIONS");
                    corsConfiguration.setAllowedOriginPatterns(s);
                    corsConfiguration.setAllowedMethods(methods);
                    corsConfiguration.setAllowedHeaders(s);
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }).and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
