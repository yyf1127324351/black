package com.back.service;

import com.back.model.UserDto;
import com.back.vo.UserVo;

import java.util.Map;

public interface UserService {
    UserDto getUserList();

    UserVo getLoginUser(Map<String, String> param);
}
