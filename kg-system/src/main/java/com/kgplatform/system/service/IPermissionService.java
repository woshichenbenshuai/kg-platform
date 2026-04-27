package com.kgplatform.system.service;

import com.kgplatform.system.domain.dto.CurrentUserPermissionDto;

public interface IPermissionService {

    CurrentUserPermissionDto getCurrentUserPermission(Long userId);
}
