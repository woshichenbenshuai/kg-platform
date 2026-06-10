package com.kgplatform.business.kinder.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.business.kinder.domain.po.DailyRecipe;
import com.kgplatform.business.kinder.domain.vo.DailyRecipeVo;
import com.kgplatform.business.kinder.mapper.DailyRecipeMapper;
import com.kgplatform.business.kinder.service.IDailyRecipeService;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class DailyRecipeServiceImpl extends ServiceImpl<DailyRecipeMapper, DailyRecipe> implements IDailyRecipeService {

    @Override
    @Transactional(readOnly = true)
    public Page<DailyRecipe> selectPage(Integer current, Integer size, DailyRecipeVo vo) {
        DailyRecipeVo queryVo = vo == null ? new DailyRecipeVo() : vo;
        return page(new Page<>(current, size), Wrappers.<DailyRecipe>lambdaQuery()
                .eq(DailyRecipe::getDeleteStatus, Boolean.FALSE)
                .eq(queryVo.getRecipeDate() != null, DailyRecipe::getRecipeDate, queryVo.getRecipeDate())
                .eq(queryVo.getMealType() != null && !queryVo.getMealType().isBlank(), DailyRecipe::getMealType, queryVo.getMealType())
                .eq(queryVo.getStatus() != null, DailyRecipe::getStatus, queryVo.getStatus())
                .orderByDesc(DailyRecipe::getRecipeDate)
                .orderByAsc(DailyRecipe::getMealType));
    }

    @Override
    public boolean saveRecipe(DailyRecipeVo vo) {
        validate(vo, false);
        DailyRecipe entity = toEntity(vo);
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        entity.setDeleteStatus(Boolean.FALSE);
        return save(entity);
    }

    @Override
    public boolean updateRecipe(DailyRecipeVo vo) {
        validate(vo, true);
        return updateById(toEntity(vo));
    }

    @Override
    public boolean deleteRecipe(Long id) {
        Asserts.notNull(id, "Recipe id is required");
        return removeById(id);
    }

    private void validate(DailyRecipeVo vo, boolean requireId) {
        Asserts.notNull(vo, "Recipe parameter is required");
        if (requireId) {
            Asserts.notNull(vo.getId(), "Recipe id is required");
        }
        Asserts.notNull(vo.getRecipeDate(), "Recipe date is required");
        Asserts.notBlank(vo.getMealType(), "Meal type is required");
        Asserts.notBlank(vo.getContent(), "Recipe content is required");
    }

    private DailyRecipe toEntity(DailyRecipeVo vo) {
        DailyRecipe entity = new DailyRecipe()
                .setRecipeDate(vo.getRecipeDate())
                .setMealType(vo.getMealType())
                .setContent(vo.getContent())
                .setStatus(vo.getStatus());
        entity.setId(vo.getId());
        return entity;
    }
}
