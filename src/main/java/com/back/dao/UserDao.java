package com.back.dao;

import com.back.model.User;
import com.back.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserDao {
    User getUserList();

    UserVo getLoginUserByMap(Map<Object, String> param);

    List<UserVo> getLoginUserByLoginName(@Param("loginName") String loginName);

    int getUserByIdAndPassword(Map<Object, String> map);

    void updateLoginPassword(Map<Object, String> map);
}
