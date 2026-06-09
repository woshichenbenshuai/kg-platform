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
 * Teacher business profile.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("teacher")
@Schema(description = "Teacher")
@EqualsAndHashCode(callSuper = true)
public class Teacher extends BaseAuditingEntity<Long> implements Serializable {

    @Schema(description = "Platform user id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "Teacher number")
    @TableField("teacher_no")
    @Size(max = 64, message = "Teacher number is too long")
    private String teacherNo;

    @Schema(description = "Teacher name")
    @TableField("teacher_name")
    @Size(max = 100, message = "Teacher name is too long")
    private String teacherName;

    @Schema(description = "Phone")
    @TableField("phone")
    @Size(max = 32, message = "Phone is too long")
    private String phone;

    @Schema(description = "Gender")
    @TableField("gender")
    @Size(max = 20, message = "Gender is too long")
    private String gender;

    @Schema(description = "Status: 1 enabled, 0 disabled")
    @TableField("status")
    private Integer status;
}
