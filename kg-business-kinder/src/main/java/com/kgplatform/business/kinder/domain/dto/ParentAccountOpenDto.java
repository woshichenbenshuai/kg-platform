package com.kgplatform.business.kinder.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 开通家长账号结果
 */
@Data
@Accessors(chain = true)
public class ParentAccountOpenDto implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long guardianId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long relationId;

    private String username;
}
