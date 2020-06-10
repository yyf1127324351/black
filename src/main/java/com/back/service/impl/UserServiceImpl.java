package com.back.service.impl;

import com.back.dao.UserDao;
import com.back.model.UserDto;
import com.back.service.UserService;
import com.back.vo.UserVo;
import com.common.BaseResponse;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserVo getLoginUserByMap(Map<Object, String> param) {
        return userDao.getLoginUserByMap(param);
    }

    @Override
    public List<UserVo> getLoginUserByLoginName(String loginName) {
        return userDao.getLoginUserByLoginName(loginName);
    }

    @Override
    public boolean checkPassword(Map<Object, String> map) {
        int count = userDao.getUserByIdAndPassword(map);
        return count > 0;

    }

    @Override
    public void updateLoginPassword(Map<Object, String> map) {
        userDao.updateLoginPassword(map);
    }

    @Override
    public BaseResponse getUerRolePageList(HashMap<String, Object> map) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setTotal(userDao.getUserRolePageCount(map));
        List<UserDto> list = userDao.getUserRolePageList(map);
        if (CollectionUtils.isNotEmpty(list)) {

        }

        //
//        List<Long> userIds = list.stream().collect()



        baseResponse.setRows(list);
        return baseResponse;
    }
}
