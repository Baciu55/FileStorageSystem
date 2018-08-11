package com.baciu.filestorage.converter;

import com.baciu.filestorage.dto.RoleDTO;
import com.baciu.filestorage.entity.Role;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleConverter {

    public RoleDTO toDTO(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public Role toEntity(RoleDTO roleDTO) {
        return Role.builder()
                .name(roleDTO.getName())
                .build();
    }

    public Set<RoleDTO> toDTO(Set<Role> roles) {
        return roles.stream()
                .map(this::toDTO)
                .collect(Collectors.toSet());
    }

    public Set<Role> toEntity(Set<RoleDTO> rolesDTO) {
        return rolesDTO.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }
}