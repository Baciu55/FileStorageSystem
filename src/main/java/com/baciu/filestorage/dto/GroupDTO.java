package com.baciu.filestorage.dto;

import lombok.*;

import javax.validation.constraints.Size;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GroupDTO {

    private Long id;
    @Size(min=3, max=30, message="group name must be longer than 3 and shorter than 30 characters")
    private String name;
    private String description;
    private Set<UserDTO> users;
    private Set<UserFileDTO> files;
    private Integer membersCount;
    private Integer filesCount;
}
