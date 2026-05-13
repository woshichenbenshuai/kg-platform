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
@TableName("growth_record")
@EqualsAndHashCode(callSuper = true)
public class GrowthRecord extends BaseAuditingEntity<Long> implements Serializable {

    @TableField("student_id")
    private Long studentId;

    @TableField("title")
    private String title;

    @TableField("content")
    private String content;

    @TableField("record_date")
    private LocalDate recordDate;

    @TableField("image_urls")
    private String imageUrls;

    @TableField("visible_to_parent")
    private Integer visibleToParent;

    @TableField("status")
    private Integer status;
}
