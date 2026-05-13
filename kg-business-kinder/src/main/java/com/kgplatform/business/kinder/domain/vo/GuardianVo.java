package com.kgplatform.business.kinder.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kgplatform.common.web.core.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

/**
 * 家长入参
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "家长")
@EqualsAndHashCode(callSuper = true)
public class GuardianVo extends BaseVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Schema(description = "平台用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long platformUserId;

    @Schema(description = "家长姓名")
    @Size(max = 100, message = "家长姓名字段过长")
    private String guardianName;

    @Schema(description = "手机号")
    @Size(max = 32, message = "手机号字段过长")
    private String phone;

    @Schema(description = "状态 1启用 0禁用")
    private Integer status;
}
