package com.lambakean.representation.dtoConverter.impl;

import com.lambakean.data.model.User;
import com.lambakean.representation.dto.UserSecurityTokensDto;
import com.lambakean.representation.dtoConverter.UserSecurityTokensDtoConverter;
import org.springframework.stereotype.Component;

@Component
public class UserSecurityTokensDtoConverterImpl implements UserSecurityTokensDtoConverter {

    @Override
    public UserSecurityTokensDto toSecurityTokensDto(User user, String accessToken, String refreshToken) {
        return new UserSecurityTokensDto(user.getId(), accessToken, refreshToken);
    }
}
