package com.kgplatform.common.security.model;

/**
 * 当前登录用户快照
 */
public class LoginUser {

    private final Long userId;

    private final String username;

    private final String nickname;

    private final Long tenantId;

    public LoginUser(Long userId, String username) {
        this(userId, username, null, null);
    }

    public LoginUser(Long userId, String username, String nickname, Long tenantId) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.tenantId = tenantId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public Long getTenantId() {
        return tenantId;
    }
}
