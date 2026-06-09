package com.kgplatform.business.kinder.domain.vo;

import com.kgplatform.common.web.core.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

/**
 * Open teacher login account request.
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Open teacher account")
public class TeacherAccountOpenVo extends BaseVo {

    @Schema(description = "Teacher number")
    @Size(max = 64, message = "Teacher number is too long")
    private String teacherNo;

    @Schema(description = "Teacher name")
    @Size(max = 100, message = "Teacher name is too long")
    private String teacherName;

    @Schema(description = "Phone, also used as login username")
    @Size(max = 32, message = "Phone is too long")
    private String phone;

    @Schema(description = "Login password")
    @Size(max = 100, message = "Password is too long")
    private String password;

    @Schema(description = "Gender")
    @Size(max = 20, message = "Gender is too long")
    private String gender;
}
