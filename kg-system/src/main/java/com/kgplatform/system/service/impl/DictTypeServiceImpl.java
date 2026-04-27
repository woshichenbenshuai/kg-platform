package com.kgplatform.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.system.domain.convert.DictTypeConverter;
import com.kgplatform.system.domain.dto.DictTypeDto;
import com.kgplatform.system.domain.po.DictType;
import com.kgplatform.system.domain.vo.DictTypeVo;
import com.kgplatform.system.mapper.DictTypeMapper;
import com.kgplatform.system.mapper.DictValueMapper;
import com.kgplatform.system.service.IDictTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 系统字典类型 Service 实现类
 * <p>
 * DictTypeServiceImpl Service 实现类
 *
 * @author kg_chen
 * @since 2023-12-29 11:06:01
 */
@Service("dictTypeService")
@Transactional(rollbackFor = Exception.class)
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType>
        implements IDictTypeService {


    /**
     * 系统字典类型 转换
     */

    private final DictTypeConverter dictTypeConverter;


    private final DictValueMapper dictValueMapper;

    /**
     * 构造函数
     *
     * @param dictTypeConverter dictTypeConverter
     * @param dictValueMapper   dictValueMapper
     */
    public DictTypeServiceImpl(DictTypeConverter dictTypeConverter,
                               DictValueMapper dictValueMapper) {
        this.dictTypeConverter = dictTypeConverter;
        this.dictValueMapper = dictValueMapper;

    }

    @Override
    @Transactional(readOnly = true)
    public Page<DictTypeDto> selectPage(Integer current,
                                        Integer size,
                                        DictTypeVo vo) {
        return baseMapper.selectPageList(new Page<>(current, size), vo);

    }



    @Override

    public Boolean update(DictTypeVo vo) {
        if (StrUtil.isNotBlank(vo.getCode())) {
            dictValueMapper.updateDictTypeCode(vo.getId(), vo.getCode());
        }
        return super.updateById(dictTypeConverter.vo2Domain(vo));
    }



    @Override
    public boolean delete(Long id) {
        dictValueMapper.deleteByDictTypeId(id);
        DictType dictType = new DictType();
        dictType.setId(id);
        dictType.setDeleteStatus(Boolean.TRUE);
        return super.updateById(dictType);
    }

}



