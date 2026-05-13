package com.kgplatform.business.kinder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.business.kinder.domain.dto.GuardianDto;
import com.kgplatform.business.kinder.domain.po.Guardian;
import com.kgplatform.business.kinder.domain.vo.GuardianVo;
import com.kgplatform.business.kinder.mapper.GuardianMapper;
import com.kgplatform.business.kinder.service.IGuardianService;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 家长 Service 实现
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GuardianServiceImpl extends ServiceImpl<GuardianMapper, Guardian> implements IGuardianService {

    @Override
    @Transactional(readOnly = true)
    public Page<GuardianDto> selectPage(Integer current, Integer size, GuardianVo vo) {
        GuardianVo queryVo = vo == null ? new GuardianVo() : vo;
        LambdaQueryWrapper<Guardian> query = Wrappers.<Guardian>lambdaQuery()
                .eq(Guardian::getDeleteStatus, Boolean.FALSE)
                .eq(queryVo.getStatus() != null, Guardian::getStatus, queryVo.getStatus())
                .eq(queryVo.getPlatformUserId() != null, Guardian::getUserId, queryVo.getPlatformUserId())
                .like(queryVo.getGuardianName() != null && !queryVo.getGuardianName().isBlank(), Guardian::getGuardianName, queryVo.getGuardianName())
                .like(queryVo.getPhone() != null && !queryVo.getPhone().isBlank(), Guardian::getPhone, queryVo.getPhone())
                .orderByDesc(Guardian::getCreateTime);
        Page<Guardian> page = page(new Page<>(current, size), query);
        Page<GuardianDto> result = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        result.setRecords(page.getRecords().stream().map(this::toDto).toList());
        return result;
    }

    @Override
    public boolean saveGuardian(GuardianVo vo) {
        Asserts.notNull(vo, "家长参数不能为空");
        Asserts.notBlank(vo.getGuardianName(), "家长姓名不能为空");
        assertUserAvailable(vo.getPlatformUserId(), null);
        Guardian entity = toEntity(vo);
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        entity.setDeleteStatus(Boolean.FALSE);
        return save(entity);
    }

    @Override
    public boolean updateGuardian(GuardianVo vo) {
        Asserts.notNull(vo, "家长参数不能为空");
        Asserts.notNull(vo.getId(), "家长主键不能为空");
        Asserts.notBlank(vo.getGuardianName(), "家长姓名不能为空");
        assertUserAvailable(vo.getPlatformUserId(), vo.getId());
        return updateById(toEntity(vo));
    }

    @Override
    public boolean deleteGuardian(Long id) {
        Asserts.notNull(id, "家长主键不能为空");
        Guardian entity = new Guardian();
        entity.setId(id);
        entity.setDeleteStatus(Boolean.TRUE);
        return updateById(entity);
    }

    private void assertUserAvailable(Long userId, Long id) {
        if (userId == null) {
            return;
        }
        long count = count(Wrappers.<Guardian>lambdaQuery()
                .eq(Guardian::getUserId, userId)
                .eq(Guardian::getDeleteStatus, Boolean.FALSE)
                .ne(id != null, Guardian::getId, id));
        Asserts.isTrue(count == 0, "平台用户已绑定其他家长");
    }

    private Guardian toEntity(GuardianVo vo) {
        Guardian entity = new Guardian()
                .setUserId(vo.getPlatformUserId())
                .setGuardianName(vo.getGuardianName())
                .setPhone(vo.getPhone())
                .setStatus(vo.getStatus());
        entity.setId(vo.getId());
        return entity;
    }

    private GuardianDto toDto(Guardian entity) {
        return new GuardianDto()
                .setId(entity.getId())
                .setUserId(entity.getUserId())
                .setGuardianName(entity.getGuardianName())
                .setPhone(entity.getPhone())
                .setStatus(entity.getStatus())
                .setDeleteStatus(entity.getDeleteStatus())
                .setCreateTime(entity.getCreateTime());
    }
}
