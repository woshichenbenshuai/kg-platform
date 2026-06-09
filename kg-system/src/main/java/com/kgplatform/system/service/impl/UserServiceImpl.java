package com.kgplatform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.UserConverter;
import com.kgplatform.system.domain.dto.UserDto;
import com.kgplatform.system.domain.po.User;
import com.kgplatform.system.domain.po.UserRole;
import com.kgplatform.system.domain.vo.UserVo;
import com.kgplatform.system.mapper.UserMapper;
import com.kgplatform.system.service.IUserRoleService;
import com.kgplatform.system.service.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
/**
 * 系统用户 Service 实现类
 * <p>
 * UserServiceImpl Service 实现类
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

@Service("userService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final IUserRoleService userRoleService;

    public UserServiceImpl(UserConverter userConverter,
                           PasswordEncoder passwordEncoder,
                           IUserRoleService userRoleService) {
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> selectPage(Integer current, Integer size, UserVo vo) {
        return baseMapper.selectPageList(new Page<>(current, size), vo);
    }

    @Override
    public boolean saveUser(UserVo vo) {
        Asserts.notNull(vo, "用户参数不能为空");
        Asserts.notBlank(vo.getUsername(), "用户名不能为空");
        Asserts.notBlank(vo.getPassword(), "密码不能为空");
        long count = baseMapper.selectCount(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, vo.getUsername())
                .eq(User::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "用户名已存在");

        User user = userConverter.vo2Domain(vo);
        user.setPassword(passwordEncoder.encode(vo.getPassword()));
        boolean saved = baseMapper.insert(user) > 0;
        if (saved) {
            syncUserRoles(user.getId(), vo.getRoleIds());
        }
        return saved;
    }

    @Override
    public Boolean update(UserVo vo) {
        boolean updated = super.updateById(userConverter.vo2Domain(vo));
        if (updated) {
            syncUserRoles(vo.getId(), vo.getRoleIds());
        }
        return updated;
    }

    @Override
    public boolean delete(Long id) {
        User user = new User();
        user.setId(id);
        user.setDeleteStatus(Boolean.TRUE);
        return super.updateById(user);
    }

    private void syncUserRoles(Long userId, List<Long> roleIds) {
        if (roleIds == null) {
            return;
        }
        Asserts.notNull(userId, "User id is required");
        List<Long> distinctRoleIds = roleIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        userRoleService.update(Wrappers.<UserRole>lambdaUpdate()
                .eq(UserRole::getUserId, userId)
                .eq(UserRole::getDeleteStatus, Boolean.FALSE)
                .notIn(!distinctRoleIds.isEmpty(), UserRole::getRoleId, distinctRoleIds)
                .set(UserRole::getDeleteStatus, Boolean.TRUE));

        if (distinctRoleIds.isEmpty()) {
            return;
        }

        for (Long roleId : distinctRoleIds) {
            UserRole existing = userRoleService.getOne(Wrappers.<UserRole>lambdaQuery()
                    .eq(UserRole::getUserId, userId)
                    .eq(UserRole::getRoleId, roleId)
                    .last("LIMIT 1"));
            if (existing == null) {
                UserRole userRole = new UserRole()
                        .setUserId(userId)
                        .setRoleId(roleId)
                        .setStatus(Boolean.TRUE);
                userRole.setDeleteStatus(Boolean.FALSE);
                userRoleService.save(userRole);
            } else if (Boolean.TRUE.equals(existing.getDeleteStatus()) || !Boolean.TRUE.equals(existing.getStatus())) {
                UserRole userRole = new UserRole()
                        .setUserId(userId)
                        .setRoleId(roleId)
                        .setStatus(Boolean.TRUE);
                userRole.setId(existing.getId());
                userRole.setDeleteStatus(Boolean.FALSE);
                userRoleService.updateById(userRole);
            }
        }
    }
}
