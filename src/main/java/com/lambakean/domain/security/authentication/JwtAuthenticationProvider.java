package com.lambakean.domain.security.authentication;

import com.lambakean.data.model.RefreshTokenWrapper;
import com.lambakean.data.model.User;
import com.lambakean.data.repository.RefreshTokenWrapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final RefreshTokenWrapperRepository refreshTokenWrapperRepository;

    @Autowired
    public JwtAuthenticationProvider(RefreshTokenWrapperRepository refreshTokenWrapperRepository) {
        this.refreshTokenWrapperRepository = refreshTokenWrapperRepository;
    }

    @Override
    public Authentication authenticate(@NonNull Authentication authentication) throws AuthenticationException {

        String token = authentication.getCredentials().toString();

        Optional<RefreshTokenWrapper> refreshTokenWrapperOptional = Optional.ofNullable(
                refreshTokenWrapperRepository.findByToken(token)
        );

        if(!refreshTokenWrapperOptional.isPresent() || !refreshTokenWrapperOptional.get().isValid()) {
            throw new BadCredentialsException(String.format("The token [%s] is invalid", token));
        }

        User user = refreshTokenWrapperOptional.get().getUser();

        Authentication authenticationToken = new JwtAuthenticationToken(token, user);
        authenticationToken.setAuthenticated(true);

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return JwtAuthenticationToken.class.equals(authenticationClass);
    }
}
