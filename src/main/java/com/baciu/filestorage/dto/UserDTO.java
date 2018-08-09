package com.baciu.filestorage.dto;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter

public class UserDTO {

    private Long id;

    @Size(min=3, max=30, message="username must be longer 3 and shorter than 30 characters")
    private String username;

    @Email(message = "invalid email format")
    @NotEmpty(message = "email can not be empty")
    private String email;

    @Size(min=3, max=30, message="password must be longer 3 and shorter than 30 characters")
    private String password;

    private Date registerDate;
    private Set<FileDTO> files;
    private Set<GroupDTO> groups;
    private Set<RoleDTO> roles;

}
