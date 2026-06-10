package com.kgplatform.business.kinder.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.business.kinder.domain.po.GrowthRecord;
import com.kgplatform.business.kinder.domain.po.Student;
import com.kgplatform.business.kinder.domain.vo.GrowthRecordVo;
import com.kgplatform.business.kinder.mapper.GrowthRecordMapper;
import com.kgplatform.business.kinder.mapper.StudentMapper;
import com.kgplatform.business.kinder.service.IGrowthRecordService;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GrowthRecordServiceImpl extends ServiceImpl<GrowthRecordMapper, GrowthRecord> implements IGrowthRecordService {

    private final StudentMapper studentMapper;

    public GrowthRecordServiceImpl(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GrowthRecord> selectPage(Integer current, Integer size, GrowthRecordVo vo) {
        GrowthRecordVo queryVo = vo == null ? new GrowthRecordVo() : vo;
        return page(new Page<>(current, size), Wrappers.<GrowthRecord>lambdaQuery()
                .eq(GrowthRecord::getDeleteStatus, Boolean.FALSE)
                .eq(queryVo.getStudentId() != null, GrowthRecord::getStudentId, queryVo.getStudentId())
                .eq(queryVo.getVisibleToParent() != null, GrowthRecord::getVisibleToParent, queryVo.getVisibleToParent())
                .eq(queryVo.getStatus() != null, GrowthRecord::getStatus, queryVo.getStatus())
                .like(queryVo.getTitle() != null && !queryVo.getTitle().isBlank(), GrowthRecord::getTitle, queryVo.getTitle())
                .orderByDesc(GrowthRecord::getRecordDate)
                .orderByDesc(GrowthRecord::getCreateTime));
    }

    @Override
    public boolean saveGrowthRecord(GrowthRecordVo vo) {
        validate(vo, false);
        GrowthRecord entity = toEntity(vo);
        if (entity.getVisibleToParent() == null) {
            entity.setVisibleToParent(1);
        }
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        entity.setDeleteStatus(Boolean.FALSE);
        return save(entity);
    }

    @Override
    public boolean updateGrowthRecord(GrowthRecordVo vo) {
        validate(vo, true);
        return updateById(toEntity(vo));
    }

    @Override
    public boolean deleteGrowthRecord(Long id) {
        Asserts.notNull(id, "Growth record id is required");
        return removeById(id);
    }

    private void validate(GrowthRecordVo vo, boolean requireId) {
        Asserts.notNull(vo, "Growth record parameter is required");
        if (requireId) {
            Asserts.notNull(vo.getId(), "Growth record id is required");
        }
        Asserts.notNull(vo.getStudentId(), "Student id is required");
        Asserts.notBlank(vo.getTitle(), "Title is required");
        Asserts.notBlank(vo.getContent(), "Content is required");
        Asserts.notNull(vo.getRecordDate(), "Record date is required");
        Student student = studentMapper.selectById(vo.getStudentId());
        Asserts.isTrue(student != null && !Boolean.TRUE.equals(student.getDeleteStatus()), "Student does not exist");
    }

    private GrowthRecord toEntity(GrowthRecordVo vo) {
        GrowthRecord entity = new GrowthRecord()
                .setStudentId(vo.getStudentId())
                .setTitle(vo.getTitle())
                .setContent(vo.getContent())
                .setRecordDate(vo.getRecordDate())
                .setImageUrls(vo.getImageUrls())
                .setVisibleToParent(vo.getVisibleToParent())
                .setStatus(vo.getStatus());
        entity.setId(vo.getId());
        return entity;
    }
}
