package com.geo_report_api.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.geo_report_api.dtos.user.UserRequestDTO;
import com.geo_report_api.dtos.user.UserResponseDTO;
import com.geo_report_api.model.Role;
import com.geo_report_api.model.UserEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    
    UserEntity toUser(UserRequestDTO userRequestDTO);

    UserResponseDTO toUserResponseDTO(UserEntity userEntity);

    List<UserResponseDTO> toUserResponseDTOList(List<UserEntity> userEntity);

    default Role map(Long roleId) {
        if (roleId == null)
            return null;
        Role role = new Role();
        role.setId(roleId);
        return role;
    }

    default Set<String> map(Set<Role> roles) {
        if (roles == null)
            return null;
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
