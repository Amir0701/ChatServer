package com.lambakean.representation.dtoConverter.impl;

import com.lambakean.data.model.User;
import com.lambakean.representation.dto.UserDto;
import com.lambakean.representation.dtoConverter.UserDtoConverter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverterImpl implements UserDtoConverter {

    @Override
    public User toUser(UserDto userDto) {

        User user = new User();

        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setNickname(userDto.getNickname());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        // todo add empty collections assignments

        return user;
    }

    @Override
    public UserDto toUserDto(User user) {

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setNickname(user.getNickname());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}