package com.back.service;

import com.back.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    String getRedirectUrl(HttpServletRequest request, UserVo userVo);
}
