package com.baciu.filestorage.dto;

import lombok.*;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RoleDTO {
    private Long id;

    @Size(min=3, max=20, message="role name must be longer than 3 and shorter than 20 characters")
    private String name;
}
