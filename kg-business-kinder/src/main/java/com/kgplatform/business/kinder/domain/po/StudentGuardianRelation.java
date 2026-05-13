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
 * 学生家长关系
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("student_guardian_relation")
@Schema(description = "学生家长关系")
@EqualsAndHashCode(callSuper = true)
public class StudentGuardianRelation extends BaseAuditingEntity<Long> implements Serializable {

    @Schema(description = "学生ID")
    @TableField("student_id")
    private Long studentId;

    @Schema(description = "家长ID")
    @TableField("guardian_id")
    private Long guardianId;

    @Schema(description = "关系类型")
    @TableField("relation_type")
    @Size(max = 30, message = "关系类型字段过长")
    private String relationType;

    @Schema(description = "是否主联系人")
    @TableField("primary_contact")
    private Integer primaryContact;

    @Schema(description = "状态 1启用 0禁用")
    @TableField("status")
    private Integer status;
}
