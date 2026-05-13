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
@TableName("daily_recipe")
@EqualsAndHashCode(callSuper = true)
public class DailyRecipe extends BaseAuditingEntity<Long> implements Serializable {

    @TableField("recipe_date")
    private LocalDate recipeDate;

    @TableField("meal_type")
    private String mealType;

    @TableField("content")
    private String content;

    @TableField("status")
    private Integer status;
}
