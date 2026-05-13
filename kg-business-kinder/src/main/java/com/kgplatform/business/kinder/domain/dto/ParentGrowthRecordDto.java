package com.kgplatform.business.kinder.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class ParentGrowthRecordDto implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long studentId;
    private String studentName;
    private String title;
    private String content;
    private LocalDate recordDate;
    private String imageUrls;
}
