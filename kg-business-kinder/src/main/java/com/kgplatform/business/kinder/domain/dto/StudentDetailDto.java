package com.kgplatform.business.kinder.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 学生详情
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "学生详情")
public class StudentDetailDto implements Serializable {

    @Schema(description = "学生信息")
    private StudentDto student;

    @Schema(description = "租户信息")
    private TenantDto tenant;

    @Schema(description = "当前登录用户昵称")
    private String currentUserNickname;
}
