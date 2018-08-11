package com.baciu.filestorage.converter;

import com.baciu.filestorage.entity.UserFile;
import com.baciu.filestorage.dto.UserFileDTO;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserFileConverter {

    public UserFile toEntity(UserFileDTO userFileDTO) {
        return UserFile.builder()
                .id(userFileDTO.getId())
                .name(userFileDTO.getName())
                .path(userFileDTO.getPath())
                .description(userFileDTO.getDescription())
                .uploadDate(userFileDTO.getUploadDate())
                .size(userFileDTO.getSize())
                .build();
    }

    public UserFileDTO toDTO(UserFile userFile) {
        return UserFileDTO.builder()
                .id(userFile.getId())
                .name(userFile.getName())
                .path(userFile.getPath())
                .description(userFile.getDescription())
                .uploadDate(userFile.getUploadDate())
                .size(userFile.getSize())
                .build();
    }

    public Set<UserFileDTO> toDTO(Set<UserFile> userFiles) {
        return userFiles.stream()
                .map(this::toDTO)
                .collect(Collectors.toSet());
    }

    public Set<UserFile> toEntity(Set<UserFileDTO> filesDTO) {
        return filesDTO.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }

}
