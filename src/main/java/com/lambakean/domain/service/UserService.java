package com.lambakean.domain.service;

import com.lambakean.representation.dto.UserDto;
import com.lambakean.representation.dto.UserSecurityTokensDto;
import org.springframework.validation.BindingResult;

public interface UserService {

    UserSecurityTokensDto register(UserDto example, BindingResult bindingResult);

}
