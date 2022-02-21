package com.lambakean.domain.service;

import com.lambakean.data.model.RefreshTokenWrapper;
import com.lambakean.data.model.User;
import com.lambakean.data.repository.UserRepository;
import com.lambakean.domain.exception.EntityAlreadyExistsException;
import com.lambakean.domain.exception.InvalidEntityException;
import com.lambakean.domain.security.authentication.JwtTokenProvider;
import com.lambakean.representation.dto.UserDto;
import com.lambakean.representation.dto.UserSecurityTokensDto;
import com.lambakean.representation.dtoConverter.UserDtoConverter;
import com.lambakean.representation.dtoConverter.UserSecurityTokensDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {

    @Value("${accessToken.validityTimeMs}")
    private Long accessTokenValidityTimeMs;

    @Value("${refreshToken.validityTimeMs}")
    private Long refreshTokenValidityTimeMs;

    private final UserDtoConverter userDtoConverter;
    private final UserSecurityTokensDtoConverter userSecurityTokensDtoConverter;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenWrapperService refreshTokenWrapperService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDtoConverter userDtoConverter,
                           UserSecurityTokensDtoConverter userSecurityTokensDtoConverter,
                           JwtTokenProvider jwtTokenProvider,
                           UserRepository userRepository,
                           RefreshTokenWrapperService refreshTokenWrapperService,
                           PasswordEncoder passwordEncoder) {
        this.userDtoConverter = userDtoConverter;
        this.userSecurityTokensDtoConverter = userSecurityTokensDtoConverter;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.refreshTokenWrapperService = refreshTokenWrapperService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserSecurityTokensDto register(UserDto example, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new InvalidEntityException(
                    bindingResult.getFieldErrors().stream()
                            .map(FieldError::getDefaultMessage)
                            .collect(Collectors.toSet())
            );
        }

        User user = userDtoConverter.toUser(example);
        user.setRefreshTokenWrappers(Collections.emptySet());
        user.setSubscriptions(Collections.emptySet());

        if(userRepository.existsByName(user.getName())) {
            throw new EntityAlreadyExistsException(String.format("User with name [%s] already exists", user.getName()));
        }

        if(userRepository.existsByNickname(user.getNickname())) {
            throw new EntityAlreadyExistsException(
                    String.format("User with nickname [%s] already exists", user.getNickname())
            );
        }

        if(userRepository.existsByEmail(user.getEmail())) {
            throw new EntityAlreadyExistsException(
                    String.format("User with email [%s] already exists", user.getEmail())
            );
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            userRepository.saveAndFlush(user);
        } catch (ConstraintViolationException e) {
            throw new InvalidEntityException(
                    e.getConstraintViolations().stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.toSet())
            );
        }

        String accessToken = jwtTokenProvider.createToken(user.getId(), accessTokenValidityTimeMs);
        String refreshToken = jwtTokenProvider.createToken(user.getId(), refreshTokenValidityTimeMs);

        RefreshTokenWrapper refreshTokenWrapper = refreshTokenWrapperService.createWrapper(
                refreshToken,
                refreshTokenValidityTimeMs,
                user
        );
        refreshTokenWrapperService.save(refreshTokenWrapper);

        return userSecurityTokensDtoConverter.toSecurityTokensDto(user, accessToken, refreshToken);
    }
}