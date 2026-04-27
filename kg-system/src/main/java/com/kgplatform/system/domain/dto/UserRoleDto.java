package com.kgplatform.system.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRoleDto {
    private Long id;
    private Long userId;
    private Long roleId;
    private Boolean status;
    private Boolean deleteStatus;
    private LocalDateTime createTime;
}
