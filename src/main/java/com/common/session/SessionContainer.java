package com.common.session;


import com.back.vo.UserVo;

/**
 * 用户session容器
 */
public class SessionContainer {

    private static final ThreadLocal<UserVo> sessionThreadLocal = new ThreadLocal<UserVo>();

    public static UserVo getSession() {
        return sessionThreadLocal.get();
    }

    public static void setSession(UserVo c) {
        sessionThreadLocal.set(c);
    }

    public static void clear() {
        sessionThreadLocal.set(null);
    }


    public static String getUserName() {
        return getSession() != null ? getSession().getUserName() : null;
    }
    public static String getLoginName() {
        return getSession() != null ? getSession().getLoginName() : null;
    }
    public static String getUserNo() {
        return getSession() != null ? getSession().getUserNo() : null;
    }
    public static Long getUserId() {
        return getSession() != null ? getSession().getUserId() : null;
    }


}
