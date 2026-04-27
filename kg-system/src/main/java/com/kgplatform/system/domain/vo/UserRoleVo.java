package com.kgplatform.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kgplatform.common.web.core.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "User role relation")
@EqualsAndHashCode(callSuper = true)
public class UserRoleVo extends BaseVo {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Schema(description = "Bound user id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long bindUserId;

    @Schema(description = "Bound role id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long bindRoleId;

    @Schema(description = "Enabled status")
    private Boolean status;
}
