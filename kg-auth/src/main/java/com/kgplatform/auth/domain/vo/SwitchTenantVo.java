package com.kgplatform.auth.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Switch current tenant request.
 */
@Data
@Accessors(chain = true)
@Schema(description = "Switch current tenant")
public class SwitchTenantVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tenantId;
}
