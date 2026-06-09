package com.kgplatform.business.kinder.web.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.business.kinder.domain.po.DailyRecipe;
import com.kgplatform.business.kinder.domain.vo.DailyRecipeVo;
import com.kgplatform.business.kinder.service.IDailyRecipeService;
import com.kgplatform.common.web.core.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@Tag(name = "DailyRecipeResource", description = "Daily recipe")
@RequestMapping(path = "/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
public class DailyRecipeResource {

    private final IDailyRecipeService recipeService;

    public DailyRecipeResource(IDailyRecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/pages")
    @Operation(summary = "Page recipes")
    public Result<Page<DailyRecipe>> selectAll(
            @Parameter(description = "Current page") @RequestParam(required = false, defaultValue = "1") Integer current,
            @Parameter(description = "Page size") @RequestParam(required = false, defaultValue = "10") Integer size,
            DailyRecipeVo vo) {
        return Result.ok(recipeService.selectPage(current, size, vo));
    }

    @PostMapping
    @Operation(summary = "Create recipe")
    public Result<Boolean> insert(@RequestBody DailyRecipeVo vo) {
        return Result.ok(recipeService.saveRecipe(vo));
    }

    @PutMapping
    @Operation(summary = "Update recipe")
    public Result<Boolean> update(@RequestBody DailyRecipeVo vo) {
        return Result.ok(recipeService.updateRecipe(vo));
    }

    @DeleteMapping
    @Operation(summary = "Delete recipe")
    public Result<Boolean> delete(@RequestParam Long id) {
        return Result.ok(recipeService.deleteRecipe(id));
    }
}
