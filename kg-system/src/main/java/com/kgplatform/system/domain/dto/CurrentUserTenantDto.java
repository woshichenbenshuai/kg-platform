package com.kgplatform.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Current user accessible tenant.
 */
@Data
@Accessors(chain = true)
@Schema(description = "Current user accessible tenant")
public class CurrentUserTenantDto implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tenantId;

    private String tenantCode;

    private String tenantName;

    private String identityType;

    private Boolean defaultFlag;
}
