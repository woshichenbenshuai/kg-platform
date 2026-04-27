package com.kgplatform.system.service;

import com.kgplatform.system.domain.dto.CurrentUserAccessDto;

public interface ICurrentUserAccessService {

    CurrentUserAccessDto getCurrentUserAccess(Long userId);
}
