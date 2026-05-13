package com.kgplatform.business.kinder.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学生家长关系返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "学生家长关系")
public class StudentGuardianRelationDto implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long studentId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long guardianId;

    private String relationType;

    private Boolean primaryContact;

    private Integer status;

    private Boolean deleteStatus;

    private LocalDateTime createTime;
}
