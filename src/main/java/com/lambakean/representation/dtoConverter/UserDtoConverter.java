package com.lambakean.representation.dtoConverter;

import com.lambakean.data.model.User;
import com.lambakean.representation.dto.UserDto;

public interface UserDtoConverter {

    User toUser(UserDto userDto);

    UserDto toUserDto(User user);
}
