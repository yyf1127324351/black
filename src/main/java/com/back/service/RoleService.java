package com.back.service;

import com.back.model.RoleAuthorityDto;
import com.back.model.RoleDto;
import com.common.BaseResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RoleService {
    BaseResponse getRolePageList(HashMap<String, Object> map);

    void updateRole(RoleDto roleDto);

    void addRole(RoleDto roleDto);

    Map<String,List> getAuthTree(Long roleId);

    void saveAuthTree(RoleAuthorityDto roleAuthorityDto);
}
