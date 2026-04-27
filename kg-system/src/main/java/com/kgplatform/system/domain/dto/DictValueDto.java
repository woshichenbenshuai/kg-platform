
package com.kgplatform.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kgplatform.common.web.core.AppConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;


import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统字典数据
 * <p>
 * DictValueDto返回对象
 *
 * @author kg_chen
 * @since 2023-12-29 11:07:02
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "系统字典数据")
public class DictValueDto implements Serializable {

    /**
     * ID主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 所属字典类型(关联dict_type表)
     */
    @Schema(description = "所属字典类型(关联dict_type表)")
    private String type;

    /**
     * 字典主表id
     */
    @Schema(description = "字典主表id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long dictTypeId;

    /**
     * 字典标签
     */
    @Schema(description = "字典标签")
    private String label;

    /**
     * 字典值
     */
    @Schema(description = "字典值")
    private String value;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remarks;

    /**
     * 状态 1启用 0禁用
     */
    @Schema(description = "状态 1启用 0禁用")
    private Boolean status;

    /**
     * 是否删除 1删除  0未删除
     */
    @Schema(description = "是否删除 1删除  0未删除")
    private Boolean deleteStatus;

    /**
     * 创建时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = AppConstant.FORMAT_PATTERN_DATE_TIME)
    private LocalDateTime createTime;
}
