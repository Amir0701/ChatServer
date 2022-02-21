package com.lambakean.representation.dtoConverter;

import com.lambakean.data.model.User;
import com.lambakean.representation.dto.UserSecurityTokensDto;

public interface UserSecurityTokensDtoConverter {
    UserSecurityTokensDto toSecurityTokensDto(User user, String accessToken, String refreshToken);
}
