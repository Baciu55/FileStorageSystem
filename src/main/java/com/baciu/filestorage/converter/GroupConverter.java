package com.baciu.filestorage.converter;

import com.baciu.filestorage.entity.Group;
import com.baciu.filestorage.dto.GroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupConverter {

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserFileConverter userFileConverter;

    public Group toEntity(GroupDTO groupDTO) {
        return Group.builder()
                .id(groupDTO.getId())
                .name(groupDTO.getName())
                .description(groupDTO.getDescription())
                .build();
    }

    public GroupDTO toDTO(Group group) {
        return GroupDTO.builder()
                .id(group.getId())
                .name(group.getName())
                .description(group.getDescription())
                .build();
    }

    public GroupDTO toDTOData(Group group) {
        return GroupDTO.builder()
                .id(group.getId())
                .name(group.getName())
                .description(group.getDescription())
                .files(userFileConverter.toDTO(group.getUserFiles()))
                .users(userConverter.toDTO(group.getUsers()))
                .build();
    }

    public Set<GroupDTO> toDTO(Set<Group> groups) {
        return groups.stream()
                .map(this::toDTO)
                .collect(Collectors.toSet());
    }

}
