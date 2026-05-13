package com.kgplatform.business.kinder.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgplatform.common.core.domain.BaseAuditingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("kindergarten_notice")
@EqualsAndHashCode(callSuper = true)
public class KindergartenNotice extends BaseAuditingEntity<Long> implements Serializable {

    @TableField("title")
    private String title;

    @TableField("content")
    private String content;

    @TableField("publish_time")
    private LocalDateTime publishTime;

    @TableField("status")
    private Integer status;
}
