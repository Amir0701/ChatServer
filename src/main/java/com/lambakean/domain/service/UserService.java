package com.lambakean.domain.service;

import com.lambakean.data.model.User;
import com.lambakean.representation.dto.PasswordDto;
import com.lambakean.representation.dto.UserDto;
import com.lambakean.representation.dto.UserSecurityTokensDto;
import org.springframework.validation.BindingResult;

import java.util.concurrent.CompletableFuture;

public interface UserService {

    CompletableFuture<UserSecurityTokensDto> register(UserDto example, BindingResult bindingResult);

    CompletableFuture<UserSecurityTokensDto> login(UserDto credentials);

    UserDto delete(Long id);

    User getCurrentUser();

    UserDto change(UserDto userDto, BindingResult bindingResult);

    UserDto changePassword(PasswordDto passwordDto, BindingResult bindingResult);

    UserDto getUser(Long id);

    UserDto getUser(String nickname);

    UserDto[] getUsersByChatId(Long id);
}
