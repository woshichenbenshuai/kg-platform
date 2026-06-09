package com.kgplatform.business.kinder.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kgplatform.common.web.core.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DailyRecipeVo extends BaseVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private LocalDate recipeDate;

    private String mealType;

    private String content;

    private Integer status;
}
