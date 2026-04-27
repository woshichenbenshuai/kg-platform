package com.kgplatform.common.security.model;

/**
 * LoginUser
 * <p>
 * LoginUser业务类
 *
 * @author kg_chen
 * @since 2026-04-22 18:50:54
 */
public class LoginUser {

    private Long userId;

    private String username;

    public LoginUser(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
