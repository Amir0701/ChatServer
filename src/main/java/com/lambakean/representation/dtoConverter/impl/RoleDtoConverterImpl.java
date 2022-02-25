package com.lambakean.representation.dtoConverter.impl;

import com.lambakean.data.model.Role;
import com.lambakean.representation.dto.RoleDto;
import com.lambakean.representation.dtoConverter.RoleDtoConverter;
import org.springframework.stereotype.Component;

@Component
public class RoleDtoConverterImpl implements RoleDtoConverter {

    @Override
    public Role toRole(RoleDto roleDto) {

        switch (roleDto) {
            case ADMIN: return Role.ADMIN;
            case CREATOR: return Role.CREATOR;
            default: return Role.MEMBER;
        }
    }

    @Override
    public RoleDto toRoleDto(Role role) {

        switch (role) {
            case ADMIN: return RoleDto.ADMIN;
            case CREATOR: return RoleDto.CREATOR;
            default: return RoleDto.MEMBER;
        }
    }
}
