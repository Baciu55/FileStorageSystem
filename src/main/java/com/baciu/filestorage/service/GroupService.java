package com.baciu.filestorage.service;

import com.baciu.filestorage.converter.FileConverter;
import com.baciu.filestorage.converter.GroupConverter;
import com.baciu.filestorage.converter.UserConverter;
import com.baciu.filestorage.dto.FileDTO;
import com.baciu.filestorage.dto.GroupDTO;
import com.baciu.filestorage.dto.UserDTO;
import com.baciu.filestorage.entity.File;
import com.baciu.filestorage.entity.Group;
import com.baciu.filestorage.entity.User;
import com.baciu.filestorage.exception.FileNotExistsException;
import com.baciu.filestorage.exception.GroupNotExistsException;
import com.baciu.filestorage.exception.UserNotExistsException;
import com.baciu.filestorage.repository.FileRepository;
import com.baciu.filestorage.repository.GroupRepository;
import com.baciu.filestorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.*;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupConverter groupConverter;

    @Autowired
    private FileConverter fileConverter;

    @Autowired
    private FileRepository fileRepository;

    public GroupDTO getGroup(Long groupId) throws GroupNotExistsException {
        return groupConverter.toDTOData(groupRepository.findById(groupId)
                .orElseThrow(GroupNotExistsException::new));
    }

    public GroupDTO addGroupUsers(UserDTO userDTO, Long groupId) throws UserNotExistsException, GroupNotExistsException {
        if (!groupRepository.existsById(groupId))
            throw new GroupNotExistsException();

        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(UserNotExistsException::new);

        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotExistsException::new);

        group.getUsers().add(user);
        return groupConverter.toDTO(groupRepository.save(group));
    }

    public GroupDTO addGroupFiles(Set<FileDTO> filesDTO, Long groupId) throws GroupNotExistsException {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotExistsException::new);

        group.getFiles().addAll(fileConverter.toEntity(filesDTO));

//        Group newGroup = Group.builder()
//                .id(group.getId())
//                .description(group.getDescription())
//                .files(group.getFiles())
//                .name(group.getName())
//                .users(group.getUsers())
//                .build();

        return groupConverter.toDTO(groupRepository.save(group));
    }

    public GroupDTO addGroupFile(FileDTO fileDTO, Long id) throws GroupNotExistsException, FileNotFoundException {
        Group group = groupRepository.findById(id)
                .orElseThrow(GroupNotExistsException::new);

        group.getFiles().add(fileConverter.toEntity(fileDTO));
        return groupConverter.toDTO(groupRepository.save(group));
    }

    public GroupDTO addGroup(GroupDTO groupDTO) {
        Group group = groupConverter.toEntity(groupDTO);
        return groupConverter.toDTO(groupRepository.save(group));
    }

    public GroupDTO updateGroup(GroupDTO groupDTO) throws GroupNotExistsException {
        groupRepository.findById(groupDTO.getId())
                .orElseThrow(GroupNotExistsException::new);
        Group newGroup = Group.builder()
                .id(groupDTO.getId())
                .name(groupDTO.getName())
                .description(groupDTO.getDescription())
                .build();
        return groupConverter.toDTO(groupRepository.save(newGroup));
    }

    public void deleteGroup(Long id) throws GroupNotExistsException {
        Group group = groupRepository.findById(id)
                .orElseThrow(GroupNotExistsException::new);

        groupRepository.delete(group);
    }

    public void deleteGroupFile(Long groupId, Long fileId) throws GroupNotExistsException, FileNotExistsException {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotExistsException::new);
        File file = fileRepository.findById(fileId)
                .orElseThrow(FileNotExistsException::new);

        Set<File> files = group.getFiles();
        files.remove(file);
        groupRepository.save(group);
    }

    public GroupDTO addUserToGroup(Long groupId, Long userId) throws GroupNotExistsException, UserNotExistsException {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotExistsException::new);
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotExistsException::new);

        group.getUsers().add(user);
        return groupConverter.toDTO(groupRepository.save(group));
    }
}
