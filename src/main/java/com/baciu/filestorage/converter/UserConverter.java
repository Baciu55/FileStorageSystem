package com.baciu.filestorage.converter;

import com.baciu.filestorage.entity.User;
import com.baciu.filestorage.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserConverter {

    @Autowired
    private FileConverter fileConverter;

    @Autowired
    private GroupConverter groupConverter;

    @Autowired
    private RoleConverter roleConverter;

    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .registerDate(userDTO.getRegisterDate())
                .build();
    }

    public UserDTO toDTO(User user, boolean includeData) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .registerDate(user.getRegisterDate())
                .build();
    }

    public Set<UserDTO> toDTO(Set<User> users) {
        Set<UserDTO> usersDTO = new HashSet<UserDTO>();
        for (User u : users)
            usersDTO.add(toDTO(u, false));
        return usersDTO;
    }

    public Set<User> toEntity(Set<UserDTO> usersDTO) {
        Set<User> users = new HashSet<>(0);
        for (UserDTO userDTO : usersDTO)
            users.add(toEntity(userDTO));
        return users;
    }

}
