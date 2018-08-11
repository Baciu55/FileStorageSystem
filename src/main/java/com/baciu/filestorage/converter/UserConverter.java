package com.baciu.filestorage.converter;

import com.baciu.filestorage.entity.User;
import com.baciu.filestorage.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserConverter {

    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .registerDate(userDTO.getRegisterDate())
                .build();
    }

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .registerDate(user.getRegisterDate())
                .build();
    }

    public Set<UserDTO> toDTO(Set<User> users) {
        return users.stream()
                .map(this::toDTO)
                .collect(Collectors.toSet());
    }

    public Set<User> toEntity(Set<UserDTO> usersDTO) {
        return usersDTO.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }

}
