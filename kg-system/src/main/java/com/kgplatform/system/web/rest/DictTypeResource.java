package com.kgplatform.system.web.rest;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.DictTypeConverter;
import com.kgplatform.system.domain.dto.DictTypeDto;
import com.kgplatform.system.domain.po.DictType;
import com.kgplatform.system.domain.vo.DictTypeVo;
import com.kgplatform.system.service.IDictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * ç³»ç»å­å
¸ç±»åæ§å¶å±
 * <p>
 * DictTypeResourceæ§å¶å±
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

@Validated
@RestController
@Tag(name = "DictTypeResource", description = "系统字典类型")
@RequestMapping(path = "/dict-types", produces = MediaType.APPLICATION_JSON_VALUE)
public class DictTypeResource {

    private final DictTypeConverter dictTypeConverter;
    private final IDictTypeService dictTypeService;

    public DictTypeResource(IDictTypeService dictTypeService, DictTypeConverter dictTypeConverter) {
        this.dictTypeService = dictTypeService;
        this.dictTypeConverter = dictTypeConverter;
    }

    @GetMapping("/pages")
    @Operation(summary = "分页查询系统字典类型")
    /**
     * 分页查询系统字典类型
     *
     * @param false 当前页码
     * @param false 每页条数
     * @return 接口结果
     */
    public Result<Page<DictTypeDto>> selectAll(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "0") Integer current,
            @Parameter(description = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer size,
            DictTypeVo vo) {
        return Result.ok(this.dictTypeService.selectPage(current, size, vo));
    }

    @GetMapping("/codes")
    @Operation(summary = "根据编码查询字典类型是否重复")
    /**
     * 根据编码查询字典类型是否重复
     *
     * @param code 字典编码
     * @return 接口结果
     */
    public Result<List<DictType>> selectByCode(@Parameter(description = "字典编码") @RequestParam String code) {
        return Result.ok(this.dictTypeService.list(Wrappers.<DictType>lambdaQuery()
                .eq(DictType::getCode, code)
                .eq(DictType::getStatus, Boolean.TRUE)
                .eq(DictType::getDeleteStatus, Boolean.FALSE)));
    }

    @GetMapping
    @Operation(summary = "根据主键查询系统字典类型")
    /**
     * 根据主键查询系统字典类型
     *
     * @param id 主键
     * @return 接口结果
     */
    public Result<DictTypeDto> selectOne(@Parameter(description = "主键") @RequestParam Long id) {
        return Result.ok(this.dictTypeConverter.domain2Dto(this.dictTypeService.getById(id)));
    }

    @PostMapping
    @Operation(summary = "新增系统字典类型")
    /**
     * 新增系统字典类型
     *
     * @param vo vo
     * @return 接口结果
     */
    public Result<Boolean> insert(@RequestBody DictTypeVo vo) {
        return Result.ok(this.dictTypeService.save(this.dictTypeConverter.vo2Domain(vo)));
    }

    @PutMapping
    @Operation(summary = "修改系统字典类型")
    /**
     * 修改系统字典类型
     *
     * @param vo vo
     * @return 接口结果
     */
    public Result<Boolean> update(@RequestBody DictTypeVo vo) {
        Asserts.notNull(vo.getId(), "主键不能为空");
        return Result.ok(this.dictTypeService.update(vo));
    }

    @DeleteMapping
    @Operation(summary = "删除系统字典类型")
    /**
     * 删除系统字典类型
     *
     * @param id 主键
     * @return 接口结果
     */
    public Result<Boolean> delete(@Parameter(description = "主键") @RequestParam("id") Long id) {
        return Result.ok(this.dictTypeService.delete(id));
    }
}
