package com.back.service;

import com.back.model.User;
import com.back.vo.UserVo;

import java.util.List;
import java.util.Map;

public interface UserService {
    User getUserList();

    UserVo getLoginUserByMap(Map<Object, String> param);

    List<UserVo> getLoginUserByLoginName(String loginName);

    boolean checkPassword(Map<Object, String> map);

    void updateLoginPassword(Map<Object, String> map);
}
