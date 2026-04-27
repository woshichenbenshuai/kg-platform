package com.kgplatform.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户角色关系返回对象
 * <p>
 * UserRoleDto返回对象
 *
 * @author kg_chen
 * @since 2026-04-27 17:26:26
 */
@Data
@Schema(description = "用户角色关系")
public class UserRoleDto {

    /**
     * 主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "主键")
    private Long id;

    /**
     * 用户主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "用户主键")
    private Long userId;

    /**
     * 角色主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "角色主键")
    private Long roleId;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private Boolean status;

    /**
     * 删除状态
     */
    @Schema(description = "删除状态")
    private Boolean deleteStatus;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
