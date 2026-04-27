package com.kgplatform.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.kgplatform.common.web.core.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

/**
 * 系统字典数据
 * <p>
 * DictValueVo入参对象
¥åå¯¹è±¡
 *
 * @author kg_chen
 * @since 2023-12-29 11:07:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "系统字典数据")
@EqualsAndHashCode(callSuper = true)
public class DictValueVo extends BaseVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 所属字典类型(关联dict_type表)
     */
    @Schema(description = "所属字典类型(关联dict_type表)")
    @Size(max = 20, message = "所属字典类型(关联dict_type表)字段过长")
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
    @Size(max = 100, message = "字典标签字段过长")
    private String label;

    /**
     * 字典值
     */
    @Schema(description = "字典值")
    @Size(max = 100, message = "字典值字段过长")
    private String value;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 255, message = "备注字段过长")
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
}
