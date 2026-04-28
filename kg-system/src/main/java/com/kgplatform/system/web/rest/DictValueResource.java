package com.kgplatform.system.web.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.DictValueConverter;
import com.kgplatform.system.domain.dto.DictValueDto;
import com.kgplatform.system.domain.vo.DictValueVo;
import com.kgplatform.system.service.IDictValueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统字典数据控制层
 * <p>
 * DictValueResource控制层
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

@Validated
@RestController
@Tag(name = "DictValueResource", description = "系统字典数据")
@RequestMapping(path = "/dict-values", produces = MediaType.APPLICATION_JSON_VALUE)
public class DictValueResource {

    private final DictValueConverter dictValueConverter;
    private final IDictValueService dictValueService;

    public DictValueResource(IDictValueService dictValueService, DictValueConverter dictValueConverter) {
        this.dictValueService = dictValueService;
        this.dictValueConverter = dictValueConverter;
    }


    /**
     * 分页查询系统字典数据
     *
     * @param current 当前页码
     * @param size    每页条数
     * @param vo      入参
     * @return 接口结果
     */
    @GetMapping("/pages")
    @Operation(summary = "分页查询系统字典数据")
    public Result<Page<DictValueDto>> selectAll(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "0") Integer current,
            @Parameter(description = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer size,
            DictValueVo vo) {
        return Result.ok(this.dictValueService.selectPage(current, size, vo));
    }


    /**
     * 根据类型查询系统字典数据
     *
     * @param type 字典类型
     * @return 接口结果
     */
    @GetMapping("/lists")
    @Operation(summary = "根据类型查询系统字典数据")
    public Result<List<DictValueDto>> selectList(@Parameter(description = "字典类型") @RequestParam String type) {
        return Result.ok(this.dictValueService.selectList(type));
    }


    /**
     * 根据编码和值查询字典数据
     *
     * @param code  字典编码
     * @param value 字典值
     * @return 接口结果
     */
    @GetMapping("/codes/value")
    @Operation(summary = "根据编码和值查询字典数据")
    public Result<List<DictValueDto>> getDictAndCodeAndValue(
            @Parameter(description = "字典编码") @RequestParam String code,
            @Parameter(description = "字典值") @RequestParam String value) {
        return Result.ok(this.dictValueService.getDictAndCodeAndValue(code, value));
    }


    /**
     * 根据编码查询启用字典数据
     *
     * @param code 字典编码
     * @return 接口结果
     */
    @GetMapping("/codes")
    @Operation(summary = "根据编码查询启用字典数据")
    public Result<List<DictValueDto>> getDictAndCode(@Parameter(description = "字典编码") @RequestParam String code) {
        return Result.ok(this.dictValueService.getDictAndCode(code));
    }


    /**
     * 根据编码查询全部字典数据
     *
     * @param code 字典编码
     * @return 接口结果
     */
    @GetMapping("/codes/all")
    @Operation(summary = "根据编码查询全部字典数据")
    public Result<List<DictValueDto>> getDictAndCodeAll(@Parameter(description = "字典编码") @RequestParam String code) {
        return Result.ok(this.dictValueService.getDictAndCodeAll(code));
    }


    /**
     * 根据主键查询系统字典数据
     *
     * @param id 主键
     * @return 接口结果
     */
    @GetMapping
    @Operation(summary = "根据主键查询系统字典数据")
    public Result<DictValueDto> selectOne(@Parameter(description = "主键") @RequestParam Long id) {
        return Result.ok(this.dictValueConverter.domain2Dto(this.dictValueService.getById(id)));
    }


    /**
     * 新增系统字典数据
     *
     * @param vo vo
     * @return 接口结果
     */
    @PostMapping
    @Operation(summary = "新增系统字典数据")
    public Result<Boolean> insert(@RequestBody DictValueVo vo) {
        return Result.ok(this.dictValueService.save(this.dictValueConverter.vo2Domain(vo)));
    }


    /**
     * 修改系统字典数据
     *
     * @param vo vo
     * @return 接口结果
     */
    @PutMapping
    @Operation(summary = "修改系统字典数据")
    public Result<Boolean> update(@RequestBody DictValueVo vo) {
        Asserts.notNull(vo.getId(), "主键不能为空");
        return Result.ok(this.dictValueService.updateById(this.dictValueConverter.vo2Domain(vo)));
    }


    /**
     * 删除系统字典数据
     *
     * @param id 主键
     * @return 接口结果
     */
    @DeleteMapping
    @Operation(summary = "删除系统字典数据")
    public Result<Boolean> delete(@Parameter(description = "主键") @RequestParam("id") Long id) {
        return Result.ok(this.dictValueService.removeById(id));
    }
}
