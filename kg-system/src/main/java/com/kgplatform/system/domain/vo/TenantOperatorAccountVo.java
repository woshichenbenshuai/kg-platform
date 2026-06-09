package com.kgplatform.system.domain.vo;

import com.kgplatform.common.web.core.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

/**
 * Open tenant operator account request.
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Open tenant operator account")
public class TenantOperatorAccountVo extends BaseVo {

    @Schema(description = "Operator name")
    @Size(max = 100, message = "Operator name is too long")
    private String nickname;

    @Schema(description = "Phone, also used as login username")
    @Size(max = 20, message = "Phone is too long")
    private String phone;

    @Schema(description = "Login password")
    @Size(max = 100, message = "Password is too long")
    private String password;
}
