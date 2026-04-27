package com.kgplatform.common.web.core;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * BaseVo
 *
 * @author chen
 * @since 2026-04-23 17:19:58
 */

@Data
@Accessors(chain = true)
public class BaseVo implements Serializable {

    /**
     * 租户id
     */
    @JsonIgnore
    @Schema(hidden = true)
    private String tenantId;

    /**
     * 用户id
     */
    @JsonIgnore
    @Schema(hidden = true)
    private String userId;

    /**
     * 平台类型
     */
    @JsonIgnore
    @Schema(hidden = true)
    private String platformType;

    /**
     * 页面id
     */
    @Schema(name = "页面id")
    private String pageId;

    /**
     * 按钮id
     */
    @Schema(name = "按钮id")
    private String buttonId;

    /**
     * traceId
     */
    @JsonIgnore
    @Schema(hidden = true)
    private String traceId;

    /**
     * 操作人
     */
    @JsonIgnore
    @Schema(hidden = true)
    private String logOperator;
}
