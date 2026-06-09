package com.kgplatform.business.kinder.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.business.kinder.domain.po.DailyRecipe;
import com.kgplatform.business.kinder.domain.vo.DailyRecipeVo;

public interface IDailyRecipeService extends IService<DailyRecipe> {

    Page<DailyRecipe> selectPage(Integer current, Integer size, DailyRecipeVo vo);

    boolean saveRecipe(DailyRecipeVo vo);

    boolean updateRecipe(DailyRecipeVo vo);

    boolean deleteRecipe(Long id);
}
