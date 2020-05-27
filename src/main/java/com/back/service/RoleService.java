package com.back.service;

import com.common.BaseResponse;

import java.util.HashMap;

public interface RoleService {
    BaseResponse getRolePageList(HashMap<String, Object> map);
}
