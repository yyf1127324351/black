package com.back.dao;

import com.back.model.UserDto;
import com.back.vo.UserVo;

import java.util.Map;

public interface UserDao {
    UserDto getUserList();

    UserVo getLoginUser(Map<String, String> param);
}
