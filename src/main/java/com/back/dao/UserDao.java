package com.back.dao;

import com.back.model.User;
import com.back.vo.UserVo;

import java.util.Map;

public interface UserDao {
    User getUserList();

    UserVo getLoginUser(Map<String, String> param);
}
