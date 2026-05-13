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
 * 班级入参
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "班级")
@EqualsAndHashCode(callSuper = true)
public class KindergartenClassVo extends BaseVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Schema(description = "班级编码")
    @Size(max = 64, message = "班级编码字段过长")
    private String classCode;

    @Schema(description = "班级名称")
    @Size(max = 100, message = "班级名称字段过长")
    private String className;

    @Schema(description = "年级名称")
    @Size(max = 100, message = "年级名称字段过长")
    private String gradeName;

    @Schema(description = "状态 1启用 0禁用")
    private Integer status;
}
