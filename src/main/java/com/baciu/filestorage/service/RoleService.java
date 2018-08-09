package com.baciu.filestorage.service;

import com.baciu.filestorage.converter.RoleConverter;
import com.baciu.filestorage.dto.RoleDTO;
import com.baciu.filestorage.entity.Role;
import com.baciu.filestorage.exception.RoleExistsException;
import com.baciu.filestorage.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleConverter roleConverter;


    public RoleDTO addRole(RoleDTO roleDTO) {
        Role role = roleConverter.toEntity(roleDTO);
        roleRepository.findByName(role.getName())
                .ifPresent(x -> new RoleExistsException());

        return roleConverter.toDTO(roleRepository.save(role));
    }

    public Set<RoleDTO> getRoles() {
        return roleConverter.toDTO((Set<Role>)roleRepository.findAll());
    }

}
