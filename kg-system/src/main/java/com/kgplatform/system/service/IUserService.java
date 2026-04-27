package com.kgplatform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.system.domain.dto.UserDto;
import com.kgplatform.system.domain.po.User;
import com.kgplatform.system.domain.vo.UserVo;

public interface IUserService extends IService<User> {

    Page<UserDto> selectPage(Integer current, Integer size, UserVo vo);

    boolean saveUser(UserVo vo);

    Boolean update(UserVo vo);

    boolean delete(Long id);
}
