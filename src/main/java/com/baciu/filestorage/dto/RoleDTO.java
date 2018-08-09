package com.baciu.filestorage.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@NoArgsConstructor
@Builder
@Getter
public class RoleDTO {
    private Long id;

    @Size(min=3, max=20, message="role name must be longer than 3 and shorter than 20 characters")
    private String name;
}
