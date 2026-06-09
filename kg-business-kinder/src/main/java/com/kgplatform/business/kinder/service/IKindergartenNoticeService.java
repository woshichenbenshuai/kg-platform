package com.kgplatform.business.kinder.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.business.kinder.domain.po.KindergartenNotice;
import com.kgplatform.business.kinder.domain.vo.KindergartenNoticeVo;

public interface IKindergartenNoticeService extends IService<KindergartenNotice> {

    Page<KindergartenNotice> selectPage(Integer current, Integer size, KindergartenNoticeVo vo);

    boolean saveNotice(KindergartenNoticeVo vo);

    boolean updateNotice(KindergartenNoticeVo vo);

    boolean deleteNotice(Long id);
}
