package com.kgplatform.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.system.domain.dto.UserDto;
import com.kgplatform.system.domain.po.User;
import com.kgplatform.system.domain.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
/**
 * 系统用户 Mapper 接口
 * <p>
 * UserMapper Mapper 接口
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页查询系统用户
     *
     * @param page 分页参数
     * @param vo 查询条件
     * @return 分页结果
     */
    Page<UserDto> selectPageList(Page<UserDto> page, @Param("vo") UserVo vo);
}
