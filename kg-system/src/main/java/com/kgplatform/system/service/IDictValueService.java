package com.kgplatform.system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.system.domain.dto.DictValueDto;
import com.kgplatform.system.domain.po.DictValue;
import com.kgplatform.system.domain.vo.DictValueVo;


import java.util.List;

/**
 * 系统字典数据
 * <p>
 * DictValue表服务实现类
 *
 * @author kg_chen
 * @since 2023-12-29 11:07:02
 */
public interface IDictValueService extends IService<DictValue> {

    /**
     * 分页查询扢有数
     *
     * @param current 当前页码，默
     * @param size    每页多少条，默认10
     * @param vo      查询入参
     * @return 扢有数
     */
    Page<DictValueDto> selectPage(Integer current, Integer size, DictValueVo vo);

    /**
     * 查询列表
     *
     * @param type 字典类型
     * @return 扢有数
     */
    List<DictValueDto> selectList(String type);

    /**
     * 根据code和查询字典
     *
     * @param code  字典编码
     * @param value 字典
     * @return 扢有数
     */
    List<DictValueDto> getDictAndCodeAndValue(String code, String value);

    /**
     * 根据code查询启用字典
     *
     * @param code 字典编码
     * @return 扢有数
     */
    List<DictValueDto> getDictAndCode(String code);

    /**
     * 根据code查询全部字典
     *
     * @param code 字典编码
     * @return 扢有数
     */
    List<DictValueDto> getDictAndCodeAll(String code);
}

