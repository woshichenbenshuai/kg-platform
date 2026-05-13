package com.kgplatform.business.kinder.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 家长端孩子信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "家长端孩子信息")
public class ParentChildDto implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Schema(description = "学号")
    private String studentNo;

    @Schema(description = "学生姓名")
    private String studentName;

    @Schema(description = "性别")
    private String gender;

    @Schema(description = "出生日期")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long classId;

    @Schema(description = "班级名称")
    private String className;

    @Schema(description = "年级名称")
    private String gradeName;

    @Schema(description = "关系类型")
    private String relationType;

    @Schema(description = "是否主联系人")
    private Boolean primaryContact;
}
