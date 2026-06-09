package com.kgplatform.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Open tenant operator account result.
 */
@Data
@Accessors(chain = true)
@Schema(description = "Open tenant operator account result")
public class TenantOperatorAccountDto implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tenantId;

    private String username;
}
