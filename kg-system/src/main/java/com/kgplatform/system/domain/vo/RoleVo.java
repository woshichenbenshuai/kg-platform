package com.kgplatform.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kgplatform.common.web.core.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

/**
 * 系统角色
 * <p>
 * RoleVo入参对象
¥åå¯¹è±¡
 *
 * @author kg_chen
 * @since 2026-04-24 22:10:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "系统角色")
@EqualsAndHashCode(callSuper = true)
public class RoleVo extends BaseVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    @Size(max = 100, message = "角色编码字段过长")
    private String roleCode;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @Size(max = 100, message = "角色名称字段过长")
    private String roleName;

    /**
     * 角色范围
     */
    @Schema(description = "角色范围")
    @Size(max = 20, message = "角色范围字段过长")
    private String roleScope;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, message = "备注字段过长")
    private String remarks;

    /**
     * 状态 1启用 0禁用
     */
    @Schema(description = "状态 1启用 0禁用")
    private Boolean status;

    /**
     * 是否删除 1删除  0未删除
     */
    @Schema(description = "是否删除 1删除  0未删除")
    private Boolean deleteStatus;
}
