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
 * Teacher request VO.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "Teacher")
@EqualsAndHashCode(callSuper = true)
public class TeacherVo extends BaseVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Schema(description = "Platform user id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long platformUserId;

    @Schema(description = "Teacher number")
    @Size(max = 64, message = "Teacher number is too long")
    private String teacherNo;

    @Schema(description = "Teacher name")
    @Size(max = 100, message = "Teacher name is too long")
    private String teacherName;

    @Schema(description = "Phone")
    @Size(max = 32, message = "Phone is too long")
    private String phone;

    @Schema(description = "Gender")
    @Size(max = 20, message = "Gender is too long")
    private String gender;

    @Schema(description = "Status: 1 enabled, 0 disabled")
    private Integer status;
}
