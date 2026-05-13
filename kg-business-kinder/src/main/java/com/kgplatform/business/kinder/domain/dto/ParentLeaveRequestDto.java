package com.kgplatform.business.kinder.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class ParentLeaveRequestDto implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long studentId;
    private String studentName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String approveStatus;
    private String approveRemark;
    private LocalDateTime createTime;
}
