package com.lambakean.representation.dtoConverter;

import com.lambakean.data.model.Role;
import com.lambakean.representation.dto.RoleDto;

public interface RoleDtoConverter {

    Role toRole(RoleDto roleDto);

    RoleDto toRoleDto(Role role);
}
