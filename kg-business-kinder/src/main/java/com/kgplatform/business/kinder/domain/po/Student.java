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
import java.time.LocalDate;

/**
 * 学生
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("student")
@Schema(description = "学生")
@EqualsAndHashCode(callSuper = true)
public class Student extends BaseAuditingEntity<Long> implements Serializable {

    @Schema(description = "学号")
    @TableField("student_no")
    @Size(max = 64, message = "学号字段过长")
    private String studentNo;

    @Schema(description = "学生姓名")
    @TableField("student_name")
    @Size(max = 100, message = "学生姓名字段过长")
    private String studentName;

    @Schema(description = "性别")
    @TableField("gender")
    @Size(max = 20, message = "性别字段过长")
    private String gender;

    @Schema(description = "出生日期")
    @TableField("birthday")
    private LocalDate birthday;

    @Schema(description = "状态 1启用 0禁用")
    @TableField("status")
    private Integer status;
}
