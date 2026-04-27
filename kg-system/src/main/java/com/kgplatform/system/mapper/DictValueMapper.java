package com.kgplatform.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.system.domain.dto.DictValueDto;
import com.kgplatform.system.domain.po.DictValue;
import com.kgplatform.system.domain.vo.DictValueVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 系统字典数据
 * <p>
 * DictValue表数据库访问
 *
 * @author kg_chen
 * @since 2023-12-29 11:07:02
 */
@Mapper
@Component
public interface DictValueMapper extends BaseMapper<DictValue> {

    /**
     * 查询列表
     *
     * @param page 分页
     * @param vo   入参
     * @return 结果
     */
    Page<DictValueDto> selectPageList(Page<DictValueDto> page,
                                      @Param("vo") DictValueVo vo);

    /**
     * 根据字典类型更新子字典的类型编码
     *
     * @param id   id
     * @param code 类型
     */
    void updateDictTypeCode(@Param("id") Long id, @Param("code") String code);

    /**
     * 根据字典类型标记删除子字
     *
     * @param id id
     */
    void deleteByDictTypeId(@Param("id") Long id);

}


