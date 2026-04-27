package com.kgplatform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.system.domain.dto.DictTypeDto;
import com.kgplatform.system.domain.po.DictType;
import com.kgplatform.system.domain.vo.DictTypeVo;
/**
 * ç³»ç»å­å¸ç±»å Service æ¥å£
 * <p>
 * IDictTypeService Service æ¥å£
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

public interface IDictTypeService extends IService<DictType> {

    Page<DictTypeDto> selectPage(Integer current, Integer size, DictTypeVo vo);

    Boolean update(DictTypeVo vo);

    boolean delete(Long id);
}
