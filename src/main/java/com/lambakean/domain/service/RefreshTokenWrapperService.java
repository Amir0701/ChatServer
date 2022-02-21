package com.lambakean.domain.service;

import com.lambakean.data.model.RefreshTokenWrapper;
import com.lambakean.data.model.User;

public interface RefreshTokenWrapperService {
    RefreshTokenWrapper createWrapper(String token, Long validityTimeMs, User user);

    void save(RefreshTokenWrapper refreshTokenWrapper);
}
