package com.kgplatform.business.kinder.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kgplatform.common.web.core.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

/**
 * 开通家长账号入参
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "开通家长账号")
public class ParentAccountOpenVo extends BaseVo {

    @Schema(description = "家长姓名")
    @Size(max = 100, message = "家长姓名字段过长")
    private String guardianName;

    @Schema(description = "手机号，同时作为登录账号")
    @Size(max = 32, message = "手机号字段过长")
    private String phone;

    @Schema(description = "登录密码")
    @Size(max = 100, message = "登录密码字段过长")
    private String password;

    @Schema(description = "学生ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long studentId;

    @Schema(description = "关系类型")
    @Size(max = 30, message = "关系类型字段过长")
    private String relationType;

    @Schema(description = "是否主联系人")
    private Boolean primaryContact;
}
