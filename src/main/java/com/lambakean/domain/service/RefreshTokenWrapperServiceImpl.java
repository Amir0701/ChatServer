package com.lambakean.domain.service;

import com.lambakean.data.model.RefreshTokenWrapper;
import com.lambakean.data.model.User;
import com.lambakean.data.repository.RefreshTokenWrapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class RefreshTokenWrapperServiceImpl implements RefreshTokenWrapperService {

    private final RefreshTokenWrapperRepository refreshTokenWrapperRepository;

    @Autowired
    public RefreshTokenWrapperServiceImpl(RefreshTokenWrapperRepository refreshTokenWrapperRepository) {
        this.refreshTokenWrapperRepository = refreshTokenWrapperRepository;
    }

    @Override
    public RefreshTokenWrapper createWrapper(String token, Long validityTimeMs, User user) {

        RefreshTokenWrapper refreshTokenWrapper = new RefreshTokenWrapper();

        refreshTokenWrapper.setToken(token);
        refreshTokenWrapper.setUser(user);
        refreshTokenWrapper.setExpiresAt(LocalDateTime.now().plus(validityTimeMs, ChronoUnit.MILLIS));

        return refreshTokenWrapper;
    }

    @Override
    public void save(RefreshTokenWrapper refreshTokenWrapper) {
        refreshTokenWrapperRepository.save(refreshTokenWrapper);
    }
}
