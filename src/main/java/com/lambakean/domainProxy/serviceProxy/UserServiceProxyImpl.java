package com.lambakean.domainProxy.serviceProxy;

import com.lambakean.representation.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserServiceProxyImpl implements UserServiceProxy {

    @Override
    public UserDto register(UserDto userDto) {
        return null;
    }
}
