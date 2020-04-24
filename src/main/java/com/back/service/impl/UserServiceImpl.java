package com.back.service.impl;

import com.back.dao.UserDao;
import com.back.model.User;
import com.back.service.UserService;
import com.back.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserList() {
        return userDao.getUserList();
    }

    @Override
    public UserVo getLoginUser(Map<String, String> param) {
        return userDao.getLoginUser(param);
    }
}
