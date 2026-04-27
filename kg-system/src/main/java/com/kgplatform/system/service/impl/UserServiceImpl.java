package com.kgplatform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.UserConverter;
import com.kgplatform.system.domain.dto.UserDto;
import com.kgplatform.system.domain.po.User;
import com.kgplatform.system.domain.vo.UserVo;
import com.kgplatform.system.mapper.UserMapper;
import com.kgplatform.system.service.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public UserServiceImpl(UserConverter userConverter, PasswordEncoder passwordEncoder) {
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
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
        return baseMapper.insert(user) > 0;
    }

    @Override
    public Boolean update(UserVo vo) {
        return super.updateById(userConverter.vo2Domain(vo));
    }

    @Override
    public boolean delete(Long id) {
        User user = new User();
        user.setId(id);
        user.setDeleteStatus(Boolean.TRUE);
        return super.updateById(user);
    }
}
