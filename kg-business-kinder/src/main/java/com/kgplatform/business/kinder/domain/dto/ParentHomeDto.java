package com.kgplatform.business.kinder.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 家长端首页
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "家长端首页")
public class ParentHomeDto implements Serializable {

    @Schema(description = "家长姓名")
    private String parentName;

    @Schema(description = "是否已绑定孩子")
    private Boolean bindStatus;

    @Schema(description = "孩子数量")
    private Integer childCount;

    @Schema(description = "我的孩子")
    private List<ParentChildDto> children = new ArrayList<>();

    @Schema(description = "最新通知")
    private List<ParentNoticeDto> notices = new ArrayList<>();

    @Schema(description = "今日食谱")
    private List<ParentRecipeDto> todayRecipes = new ArrayList<>();
}
