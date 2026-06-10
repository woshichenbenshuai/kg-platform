package com.kgplatform.business.kinder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.business.kinder.domain.dto.KindergartenClassDto;
import com.kgplatform.business.kinder.domain.po.KindergartenClass;
import com.kgplatform.business.kinder.domain.vo.KindergartenClassVo;
import com.kgplatform.business.kinder.mapper.KindergartenClassMapper;
import com.kgplatform.business.kinder.service.IKindergartenClassService;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 班级 Service 实现
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class KindergartenClassServiceImpl extends ServiceImpl<KindergartenClassMapper, KindergartenClass> implements IKindergartenClassService {

    @Override
    @Transactional(readOnly = true)
    public Page<KindergartenClassDto> selectPage(Integer current, Integer size, KindergartenClassVo vo) {
        KindergartenClassVo queryVo = vo == null ? new KindergartenClassVo() : vo;
        LambdaQueryWrapper<KindergartenClass> query = Wrappers.<KindergartenClass>lambdaQuery()
                .eq(KindergartenClass::getDeleteStatus, Boolean.FALSE)
                .eq(queryVo.getStatus() != null, KindergartenClass::getStatus, queryVo.getStatus())
                .eq(queryVo.getHeadTeacherId() != null, KindergartenClass::getHeadTeacherId, queryVo.getHeadTeacherId())
                .like(queryVo.getClassName() != null && !queryVo.getClassName().isBlank(), KindergartenClass::getClassName, queryVo.getClassName())
                .eq(queryVo.getClassCode() != null && !queryVo.getClassCode().isBlank(), KindergartenClass::getClassCode, queryVo.getClassCode())
                .orderByDesc(KindergartenClass::getCreateTime);
        Page<KindergartenClass> page = page(new Page<>(current, size), query);
        Page<KindergartenClassDto> result = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        result.setRecords(page.getRecords().stream().map(this::toDto).toList());
        return result;
    }

    @Override
    public boolean saveClass(KindergartenClassVo vo) {
        Asserts.notNull(vo, "班级参数不能为空");
        Asserts.notBlank(vo.getClassCode(), "班级编码不能为空");
        Asserts.notBlank(vo.getClassName(), "班级名称不能为空");
        assertClassCodeAvailable(vo.getClassCode(), null);
        KindergartenClass entity = toEntity(vo);
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        entity.setDeleteStatus(Boolean.FALSE);
        return save(entity);
    }

    @Override
    public boolean updateClass(KindergartenClassVo vo) {
        Asserts.notNull(vo, "班级参数不能为空");
        Asserts.notNull(vo.getId(), "班级主键不能为空");
        Asserts.notBlank(vo.getClassCode(), "班级编码不能为空");
        Asserts.notBlank(vo.getClassName(), "班级名称不能为空");
        assertClassCodeAvailable(vo.getClassCode(), vo.getId());
        return updateById(toEntity(vo));
    }

    @Override
    public boolean deleteClass(Long id) {
        Asserts.notNull(id, "班级主键不能为空");
        return removeById(id);
    }

    private void assertClassCodeAvailable(String classCode, Long id) {
        long count = count(Wrappers.<KindergartenClass>lambdaQuery()
                .eq(KindergartenClass::getClassCode, classCode)
                .eq(KindergartenClass::getDeleteStatus, Boolean.FALSE)
                .ne(id != null, KindergartenClass::getId, id));
        Asserts.isTrue(count == 0, "班级编码已存在");
    }

    private KindergartenClass toEntity(KindergartenClassVo vo) {
        KindergartenClass entity = new KindergartenClass()
                .setClassCode(vo.getClassCode())
                .setClassName(vo.getClassName())
                .setGradeName(vo.getGradeName())
                .setHeadTeacherId(vo.getHeadTeacherId())
                .setStatus(vo.getStatus());
        entity.setId(vo.getId());
        return entity;
    }

    private KindergartenClassDto toDto(KindergartenClass entity) {
        return new KindergartenClassDto()
                .setId(entity.getId())
                .setClassCode(entity.getClassCode())
                .setClassName(entity.getClassName())
                .setGradeName(entity.getGradeName())
                .setHeadTeacherId(entity.getHeadTeacherId())
                .setStatus(entity.getStatus())
                .setDeleteStatus(entity.getDeleteStatus())
                .setCreateTime(entity.getCreateTime());
    }
}
