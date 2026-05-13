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
 * 学生家长关系入参
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "学生家长关系")
@EqualsAndHashCode(callSuper = true)
public class StudentGuardianRelationVo extends BaseVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Schema(description = "学生ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long studentId;

    @Schema(description = "家长ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long guardianId;

    @Schema(description = "关系类型")
    @Size(max = 30, message = "关系类型字段过长")
    private String relationType;

    @Schema(description = "是否主联系人")
    private Boolean primaryContact;

    @Schema(description = "状态 1启用 0禁用")
    private Integer status;
}
