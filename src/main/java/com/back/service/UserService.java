package com.back.service;

import com.back.model.User;
import com.back.vo.UserVo;

import java.util.Map;

public interface UserService {
    User getUserList();

    UserVo getLoginUser(Map<String, String> param);
}
