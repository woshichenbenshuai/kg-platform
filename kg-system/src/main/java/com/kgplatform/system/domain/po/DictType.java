
package com.kgplatform.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import com.kgplatform.common.core.domain.BaseAuditingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 系统字典类型
 * <p>
 * DictType实体类
 *
 * @author kg_chen
 * @since 2023-12-29 11:05:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("dict_type")
@Schema(description = "系统字典类型")
@EqualsAndHashCode(callSuper = true)
public class DictType extends BaseAuditingEntity<Long> implements Serializable {

    /**
     * 类型名称
     */
    @Schema(description = "类型名称")
    @TableField(value = "name")
    @Size(max = 100, message = "类型名称字段过长")
    private String name;

    /**
     * 类型编码
     */
    @Schema(description = "类型编码")
    @TableField(value = "code")
    @Size(max = 100, message = "类型编码字段过长")
    private String code;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @TableField(value = "remarks")
    @Size(max = 255, message = "备注字段过长")
    private String remarks;

    /**
     * 状态 1启用 0禁用
     */
    @Schema(description = "状态 1启用 0禁用")
    @TableField(value = "status")
    private Boolean status;

    /**
     * 是否删除 1删除  0未删除
     */
    @Schema(description = "是否删除 1删除  0未删除")
    @TableField(value = "delete_status")
    private Boolean deleteStatus;
}
