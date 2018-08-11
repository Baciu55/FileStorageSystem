package com.baciu.filestorage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserFileDTO {

    private Long id;

    @NotEmpty
    private String path;

    @Size(min=1, max=30, message="file name must be longer than 1 and shorter than 30 characters")
    private String name;
    private String description;
    private Date uploadDate;
    private Long size;
    private UserDTO user;
    private Set<GroupDTO> groups;
}
