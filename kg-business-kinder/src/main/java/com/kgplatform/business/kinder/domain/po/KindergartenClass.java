package com.kgplatform.business.kinder.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgplatform.common.core.domain.BaseAuditingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 班级
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("kindergarten_class")
@Schema(description = "班级")
@EqualsAndHashCode(callSuper = true)
public class KindergartenClass extends BaseAuditingEntity<Long> implements Serializable {

    @Schema(description = "班级编码")
    @TableField("class_code")
    @Size(max = 64, message = "班级编码字段过长")
    private String classCode;

    @Schema(description = "班级名称")
    @TableField("class_name")
    @Size(max = 100, message = "班级名称字段过长")
    private String className;

    @Schema(description = "年级名称")
    @TableField("grade_name")
    @Size(max = 100, message = "年级名称字段过长")
    private String gradeName;

    @Schema(description = "状态 1启用 0禁用")
    @TableField("status")
    private Integer status;
}
