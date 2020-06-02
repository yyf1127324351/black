package com.back.model;

import lombok.Data;

@Data
public class RoleAuthorityDto {
    private Long id;
    private Long roleId;
    private Long authId;
    private Integer type;
}
