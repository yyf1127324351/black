package com.back.dao;

import com.back.model.RoleDto;
import com.back.model.UserDto;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface RoleDao {
    Long getRolePageCount(HashMap<String, Object> map);

    List<RoleDto> getRolePageList(HashMap<String, Object> map);

    void updateRole(RoleDto roleDto);

    void addRole(RoleDto roleDto);

    List<RoleDto> getRoleList(RoleDto roleDto);

    List<UserDto> getUserRoleByUserIds(@Param("userIds") List<Long> userIds);
}
