package com.lambakean.domain.security.authentication;

import com.lambakean.data.model.User;
import com.lambakean.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Autowired
    public JwtAuthenticationProvider(JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(@NonNull Authentication authentication) throws AuthenticationException {

        String token = authentication.getCredentials().toString();

        Long userId = jwtTokenProvider.getUserId(token);

        if(jwtTokenProvider.isExpired(token)) {
            throw new BadCredentialsException(String.format("The access token [%s] is invalid", token));
        }

        User currentUser = userRepository.findById(userId).orElseThrow(
                () -> new BadCredentialsException(String.format(
                        "The token [%s] is valid, but the user with id [%s] couln't be found.",
                        token,
                        userId)
                )
        );


        Authentication authenticationToken = new JwtAuthenticationToken(token, currentUser);
        authenticationToken.setAuthenticated(true);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        System.out.printf("THE CURRENT USER IS [%s]\n", currentUser.getNickname());  // todo remove

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return JwtAuthenticationToken.class.equals(authenticationClass);
    }
}
