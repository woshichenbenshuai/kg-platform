package com.kgplatform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.system.domain.dto.UserDto;
import com.kgplatform.system.domain.po.User;
import com.kgplatform.system.domain.vo.UserVo;
/**
 * 系统用户 Service 接口
 * <p>
 * IUserService Service 接口
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

public interface IUserService extends IService<User> {

    Page<UserDto> selectPage(Integer current, Integer size, UserVo vo);

    boolean saveUser(UserVo vo);

    Boolean update(UserVo vo);

    boolean delete(Long id);
}
