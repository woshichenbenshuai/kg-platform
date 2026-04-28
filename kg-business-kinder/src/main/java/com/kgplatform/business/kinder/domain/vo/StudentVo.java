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
 * 学生查询入参
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "学生")
@EqualsAndHashCode(callSuper = true)
public class StudentVo extends BaseVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Schema(description = "学号")
    @Size(max = 64, message = "学号字段过长")
    private String studentNo;

    @Schema(description = "学生姓名")
    @Size(max = 100, message = "学生姓名字段过长")
    private String studentName;

    @Schema(description = "状态 1启用 0禁用")
    private Integer status;
}
