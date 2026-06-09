package com.kgplatform.business.kinder.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.business.kinder.domain.po.GrowthRecord;
import com.kgplatform.business.kinder.domain.vo.GrowthRecordVo;

public interface IGrowthRecordService extends IService<GrowthRecord> {

    Page<GrowthRecord> selectPage(Integer current, Integer size, GrowthRecordVo vo);

    boolean saveGrowthRecord(GrowthRecordVo vo);

    boolean updateGrowthRecord(GrowthRecordVo vo);

    boolean deleteGrowthRecord(Long id);
}
