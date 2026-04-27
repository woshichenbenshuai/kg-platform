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

/**
 * 用户角色关系
 * <p>
 * UserRoleVo入参对象
 *
 * @author kg_chen
 * @since 2026-04-27 17:26:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "用户角色关系")
@EqualsAndHashCode(callSuper = true)
public class UserRoleVo extends BaseVo {

    /**
     * 主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "主键")
    private Long id;

    /**
     * 绑定用户主键
     */
    @Schema(description = "绑定用户主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long bindUserId;

    /**
     * 绑定角色主键
     */
    @Schema(description = "绑定角色主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long bindRoleId;

    /**
     * 状态 1启用 0禁用
     */
    @Schema(description = "状态 1启用 0禁用")
    private Boolean status;
}
