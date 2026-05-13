package com.kgplatform.business.kinder.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgplatform.common.core.domain.BaseAuditingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
@TableName("leave_request")
@EqualsAndHashCode(callSuper = true)
public class LeaveRequest extends BaseAuditingEntity<Long> implements Serializable {

    @TableField("student_id")
    private Long studentId;

    @TableField("guardian_id")
    private Long guardianId;

    @TableField("start_date")
    private LocalDate startDate;

    @TableField("end_date")
    private LocalDate endDate;

    @TableField("reason")
    private String reason;

    @TableField("approve_status")
    private String approveStatus;

    @TableField("approve_remark")
    private String approveRemark;

    @TableField("status")
    private Integer status;
}
