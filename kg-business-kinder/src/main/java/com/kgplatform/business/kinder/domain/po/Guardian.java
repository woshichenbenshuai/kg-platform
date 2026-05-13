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
 * 家长/监护人
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("guardian")
@Schema(description = "家长")
@EqualsAndHashCode(callSuper = true)
public class Guardian extends BaseAuditingEntity<Long> implements Serializable {

    @Schema(description = "平台用户ID")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "家长姓名")
    @TableField("guardian_name")
    @Size(max = 100, message = "家长姓名字段过长")
    private String guardianName;

    @Schema(description = "手机号")
    @TableField("phone")
    @Size(max = 32, message = "手机号字段过长")
    private String phone;

    @Schema(description = "状态 1启用 0禁用")
    @TableField("status")
    private Integer status;
}
