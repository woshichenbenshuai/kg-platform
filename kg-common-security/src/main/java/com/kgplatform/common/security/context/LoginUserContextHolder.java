package com.kgplatform.common.security.context;

import com.kgplatform.common.security.model.LoginUser;
import com.kgplatform.common.web.core.Status;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.core.NamedThreadLocal;

/**
 * 当前登录用户上下文
 */
public final class LoginUserContextHolder {

    private static final ThreadLocal<LoginUser> LOGIN_USER_HOLDER = new NamedThreadLocal<>("kg-login-user");

    private LoginUserContextHolder() {
    }

    public static void set(LoginUser loginUser) {
        LOGIN_USER_HOLDER.set(loginUser);
    }

    public static LoginUser get() {
        return LOGIN_USER_HOLDER.get();
    }

    public static LoginUser require() {
        LoginUser loginUser = get();
        Asserts.notNull(loginUser, Status.UNAUTHORIZED);
        return loginUser;
    }

    public static void clear() {
        LOGIN_USER_HOLDER.remove();
    }
}
