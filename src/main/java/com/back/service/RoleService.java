package com.back.service;

import com.back.model.RoleDto;
import com.common.BaseResponse;

import java.util.HashMap;

public interface RoleService {
    BaseResponse getRolePageList(HashMap<String, Object> map);

    void updateRole(RoleDto roleDto);

    void addRole(RoleDto roleDto);
}
