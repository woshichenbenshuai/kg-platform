package com.kgplatform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.system.domain.convert.DictValueConverter;
import com.kgplatform.system.domain.dto.DictValueDto;
import com.kgplatform.system.domain.po.DictValue;
import com.kgplatform.system.domain.vo.DictValueVo;
import com.kgplatform.system.mapper.DictValueMapper;
import com.kgplatform.system.service.IDictValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


/**
 * 系统字典数据
 * <p>
 * DictValue表服务实现类
 *
 * @author kg_chen
 * @since 2023-12-29 11:07:03
 */

@Slf4j
@Service("dictValueService")
@Transactional(rollbackFor = Exception.class)
public class DictValueServiceImpl extends ServiceImpl<DictValueMapper, DictValue>
    implements IDictValueService {


    /**
     * 系统字典数据 转换
     */
    private final DictValueConverter dictValueConverter;


    /**
     * 构造函数
     *
     * @param dictValueConverter 转换
     */
    public DictValueServiceImpl(DictValueConverter dictValueConverter) {
        this.dictValueConverter = dictValueConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DictValueDto> selectPage(Integer current,
                                         Integer size,
                                         DictValueVo vo) {
        return baseMapper.selectPageList(new Page<>(current, size), vo);
    }


    @Override
    public List<DictValueDto> selectList(String type) {
        List<DictValue> list = super.list(Wrappers.<DictValue>lambdaQuery()
            .eq(DictValue::getType, type)
            .eq(DictValue::getDeleteStatus, Boolean.FALSE)
            .eq(DictValue::getStatus, Boolean.TRUE));
        return dictValueConverter.domains2Dtos(list);

    }



    @Override
    public List<DictValueDto> getDictAndCodeAndValue(String code, String value) {
        List<DictValue> list = super.list(Wrappers.<DictValue>lambdaQuery()
            .eq(DictValue::getType, code)
            .eq(DictValue::getValue, value)
            .eq(DictValue::getDeleteStatus, Boolean.FALSE));
        return dictValueConverter.domains2Dtos(list);
    }


    @Override
    public List<DictValueDto> getDictAndCode(String code) {
        List<DictValue> list = super.list(Wrappers.<DictValue>lambdaQuery()
            .eq(DictValue::getType, code)
            .eq(DictValue::getStatus, Boolean.TRUE)
            .eq(DictValue::getDeleteStatus, Boolean.FALSE));
        return dictValueConverter.domains2Dtos(list);
    }


    @Override
    public List<DictValueDto> getDictAndCodeAll(String code) {
        List<DictValue> list = super.list(Wrappers.<DictValue>lambdaQuery()
            .eq(DictValue::getType, code)
            .eq(DictValue::getDeleteStatus, Boolean.FALSE));
        return dictValueConverter.domains2Dtos(list);
    }

}





