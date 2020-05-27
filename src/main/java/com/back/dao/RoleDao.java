package com.back.dao;

import com.back.model.RoleDto;

import java.util.HashMap;
import java.util.List;

public interface RoleDao {
    Long getRolePageCount(HashMap<String, Object> map);

    List<RoleDto> getRolePageList(HashMap<String, Object> map);

    void updateRole(RoleDto roleDto);
}
