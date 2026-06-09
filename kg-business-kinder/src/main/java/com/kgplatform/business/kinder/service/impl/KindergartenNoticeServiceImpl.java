package com.kgplatform.business.kinder.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.business.kinder.domain.po.KindergartenNotice;
import com.kgplatform.business.kinder.domain.vo.KindergartenNoticeVo;
import com.kgplatform.business.kinder.mapper.KindergartenNoticeMapper;
import com.kgplatform.business.kinder.service.IKindergartenNoticeService;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(rollbackFor = Exception.class)
public class KindergartenNoticeServiceImpl extends ServiceImpl<KindergartenNoticeMapper, KindergartenNotice>
        implements IKindergartenNoticeService {

    @Override
    @Transactional(readOnly = true)
    public Page<KindergartenNotice> selectPage(Integer current, Integer size, KindergartenNoticeVo vo) {
        KindergartenNoticeVo queryVo = vo == null ? new KindergartenNoticeVo() : vo;
        return page(new Page<>(current, size), Wrappers.<KindergartenNotice>lambdaQuery()
                .eq(KindergartenNotice::getDeleteStatus, Boolean.FALSE)
                .eq(queryVo.getStatus() != null, KindergartenNotice::getStatus, queryVo.getStatus())
                .like(queryVo.getTitle() != null && !queryVo.getTitle().isBlank(), KindergartenNotice::getTitle, queryVo.getTitle())
                .orderByDesc(KindergartenNotice::getPublishTime)
                .orderByDesc(KindergartenNotice::getCreateTime));
    }

    @Override
    public boolean saveNotice(KindergartenNoticeVo vo) {
        validate(vo, false);
        KindergartenNotice entity = toEntity(vo);
        if (entity.getPublishTime() == null) {
            entity.setPublishTime(LocalDateTime.now());
        }
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        entity.setDeleteStatus(Boolean.FALSE);
        return save(entity);
    }

    @Override
    public boolean updateNotice(KindergartenNoticeVo vo) {
        validate(vo, true);
        return updateById(toEntity(vo));
    }

    @Override
    public boolean deleteNotice(Long id) {
        Asserts.notNull(id, "Notice id is required");
        KindergartenNotice entity = new KindergartenNotice();
        entity.setId(id);
        entity.setDeleteStatus(Boolean.TRUE);
        return updateById(entity);
    }

    private void validate(KindergartenNoticeVo vo, boolean requireId) {
        Asserts.notNull(vo, "Notice parameter is required");
        if (requireId) {
            Asserts.notNull(vo.getId(), "Notice id is required");
        }
        Asserts.notBlank(vo.getTitle(), "Notice title is required");
        Asserts.notBlank(vo.getContent(), "Notice content is required");
    }

    private KindergartenNotice toEntity(KindergartenNoticeVo vo) {
        KindergartenNotice entity = new KindergartenNotice()
                .setTitle(vo.getTitle())
                .setContent(vo.getContent())
                .setPublishTime(vo.getPublishTime())
                .setStatus(vo.getStatus());
        entity.setId(vo.getId());
        return entity;
    }
}
