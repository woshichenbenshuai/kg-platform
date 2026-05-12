package com.kgplatform.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 角色菜单批量授权入参。
 */
@Data
@Schema(description = "角色菜单批量授权")
public class RoleMenuBatchSaveVo {

    @Schema(description = "角色主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long roleId;

    @Schema(description = "菜单主键集合")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private List<Long> menuIds;
}
