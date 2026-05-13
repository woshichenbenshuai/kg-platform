package com.kgplatform.business.kinder.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kgplatform.common.web.core.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ParentLeaveRequestVo extends BaseVo {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long studentId;
    private LocalDate startDate;
    private LocalDate endDate;
    @Size(max = 500, message = "请假原因字段过长")
    private String reason;
}
