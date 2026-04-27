package com.kgplatform.common.core.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础审计实体
 * <p>
 * 统一封装主键 审计字段 逻辑删除字段
 *
 * @author kg_chen
 * @since 2026-04-23 16:10:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class BaseAuditingEntity<T extends Serializable> implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Schema(description = "主键ID")
    private T id;

    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @Schema(description = "创建者ID")
    private String createBy;

    @TableField(value = "last_modified_by", fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "修改者ID")
    private String lastModifiedBy;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField(value = "last_modified_time", fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "修改时间")
    private LocalDateTime lastModifiedTime;

    @TableLogic(value = "b'0'", delval = "b'1'")
    @TableField(value = "delete_status", fill = FieldFill.INSERT)
    @Schema(description = "逻辑删除状态")
    private Boolean deleteStatus;
}

