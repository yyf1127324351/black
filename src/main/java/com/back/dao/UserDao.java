package com.back.dao;

import com.back.model.UserDto;
import com.back.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserDao {

    UserVo getLoginUserByMap(Map<Object, String> param);

    List<UserVo> getLoginUserByLoginName(@Param("loginName") String loginName);

    int getUserByIdAndPassword(Map<Object, String> map);

    void updateLoginPassword(Map<Object, String> map);

    List<UserDto> getUserRolePageList(HashMap<String, Object> map);

    Long getUserRolePageCount(HashMap<String, Object> map);

    List<Long> getUserRoleIds(@Param("userId") Long userId);
}
