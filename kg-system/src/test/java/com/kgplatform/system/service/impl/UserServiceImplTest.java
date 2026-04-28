package com.kgplatform.system.service.impl;

import com.kgplatform.common.web.exception.ApiException;
import com.kgplatform.system.domain.convert.UserConverter;
import com.kgplatform.system.domain.po.User;
import com.kgplatform.system.domain.vo.UserVo;
import com.kgplatform.system.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class UserServiceImplTest {

    @Test
    void save_should_reject_duplicate_username() throws Exception {
        UserMapper userMapper = mock(UserMapper.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserServiceImpl service = new UserServiceImpl(UserConverter.INSTANCE, passwordEncoder);
        setBaseMapper(service, userMapper);

        doReturn(1L).when(userMapper).selectCount(any());

        UserVo vo = new UserVo().setUsername("admin").setPassword("123456").setStatus(1);

        assertThrows(ApiException.class, () -> service.saveUser(vo));
        verify(userMapper, never()).insert(any(User.class));
    }

    @Test
    void save_should_encode_password_before_insert() throws Exception {
        UserMapper userMapper = mock(UserMapper.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserServiceImpl service = new UserServiceImpl(UserConverter.INSTANCE, passwordEncoder);
        setBaseMapper(service, userMapper);

        doReturn(0L).when(userMapper).selectCount(any());

        UserVo vo = new UserVo().setUsername("admin").setPassword("123456").setStatus(1);

        service.saveUser(vo);

        org.mockito.ArgumentCaptor<User> captor = org.mockito.ArgumentCaptor.forClass(User.class);
        verify(userMapper).insert(captor.capture());
        assertNotEquals("123456", captor.getValue().getPassword());
        assertTrue(passwordEncoder.matches("123456", captor.getValue().getPassword()));
    }

    private void setBaseMapper(UserServiceImpl service, UserMapper userMapper) throws Exception {
        ReflectionTestUtils.setField(service, "baseMapper", userMapper);
    }
}
