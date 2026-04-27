package com.kgplatform.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.system.domain.dto.DictTypeDto;
import com.kgplatform.system.domain.po.DictType;
import com.kgplatform.system.domain.vo.DictTypeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 系统字典类型
 * <p>
 * DictType表数据库访问
 *
 * @author kg_chen
 * @since 2023-12-29 11:05:49
 */
@Mapper
@Component
public interface DictTypeMapper extends BaseMapper<DictType> {


    /**
     * 查询列表
     *
     * @param page 分页
     * @param vo   入参
     * @return 结果
     */
    Page<DictTypeDto> selectPageList(Page<DictTypeDto> page,
                                     @Param("vo") DictTypeVo vo);
}

