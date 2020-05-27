package com.back.service.impl;

import com.back.dao.RoleDao;
import com.back.model.RoleDto;
import com.back.service.RoleService;
import com.common.BaseResponse;
import com.common.session.SessionContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleDao roleDao;

    @Override
    public BaseResponse getRolePageList(HashMap<String, Object> map) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setTotal(roleDao.getRolePageCount(map));
        List<RoleDto> list = roleDao.getRolePageList(map);
        baseResponse.setRows(list);
        return baseResponse;
    }

    @Override
    public void updateRole(RoleDto roleDto) {
        roleDto.setUpdateUser(SessionContainer.getUserId());
        roleDto.setUpdateTime(new Date());
        roleDao.updateRole(roleDto);
    }

    @Override
    public void addRole(RoleDto roleDto) {
        roleDto.setCreateUser(SessionContainer.getUserId());
        roleDao.addRole(roleDto);
    }
}
