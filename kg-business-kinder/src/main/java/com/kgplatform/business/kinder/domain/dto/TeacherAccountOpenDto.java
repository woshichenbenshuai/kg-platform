package com.kgplatform.business.kinder.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Open teacher account response.
 */
@Data
@Accessors(chain = true)
@Schema(description = "Open teacher account result")
public class TeacherAccountOpenDto implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long teacherId;

    private String username;
}
