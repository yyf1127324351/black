package com.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    /**
     * 设置cookie
     * @param name cookieName
     * @param value cookieValue
     * @param domain 域
     * @param expire 过期时长
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String domain, int expire) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setPath("/");
        if (expire >= 0) {
            cookie.setMaxAge(expire);
        }
        response.addCookie(cookie);
    }
    /**
     * 设置cookie
     * @param name cookieName
     * @param value cookieValue
     * @param expire 过期时长
     */
    public static void setCookie(HttpServletResponse response, String name, String value,int expire) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (expire >= 0) {
            cookie.setMaxAge(expire);
        }
        response.addCookie(cookie);
    }


    /**
     * 根据cookie名称获取cookie
     *
     * @param httpServletRequest httpServletRequest
     * @param cookieName         cookie名称
     * @return cookie值
     */
    public static String getCookieValue(HttpServletRequest httpServletRequest, String cookieName) {
        String cookieValue = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equalsIgnoreCase(cookieName, cookie.getName())) {
                    cookieValue = cookie.getValue();
                    break;
                }
            }
        }
        return cookieValue;
    }
}
