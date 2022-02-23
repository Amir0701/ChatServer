package com.lambakean.domain.service;

import com.lambakean.data.model.User;
import com.lambakean.representation.dto.UserDto;
import com.lambakean.representation.dto.UserSecurityTokensDto;
import org.springframework.validation.BindingResult;

public interface UserService {

    UserSecurityTokensDto register(UserDto example, BindingResult bindingResult);

    UserSecurityTokensDto login(UserDto credentials);

    UserDto delete(Long id);

    User getCurrentUser();

    UserDto change(UserDto userDto, BindingResult bindingResult);
}
