package com.back.service.impl;

import com.back.dao.RoleDao;
import com.back.dao.UserDao;
import com.back.model.UserDto;
import com.back.service.UserService;
import com.back.vo.UserVo;
import com.common.BaseResponse;
import com.common.session.SessionContainer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    RoleDao roleDao;

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
            List<Long> userIds = list.stream().map(UserDto::getId).collect(Collectors.toList());
            List<UserDto> userRoles = roleDao.getUserRoleByUserIds(userIds);
        }





        baseResponse.setRows(list);
        return baseResponse;
    }

    @Override
    public BaseResponse saveUserRole(UserDto userDto) {
        //方法1：查出该员工原有角色，与新角色进行对比，只新增新的角色；
//        List<Long> userRoleIdsOld = userDao.getUserRoleIds(userDto.getUserId());
//        List<Long> userRoleIdsNew = userDto.getUserRoleList();

        //userRoleIdsOld - userRoleIdsNew = 需要删除的角色
//        List<Long> needDelRoles = userRoleIdsOld.stream().filter(item -> !userRoleIdsNew.contains(item)).collect(toList());
        //userRoleIdsNew - userRoleIdsOld = 需要添加的角色
//        List<Long> needAddRoles = userRoleIdsNew.stream().filter(item -> !userRoleIdsOld.contains(item)).collect(toList());





        //方法2：删除该员工所有角色，重新新增角色。


        userDto.setDeleted(0);
        userDto.setCreateTime(new Date());
        userDto.setCreateUser(SessionContainer.getUserId());
        return null;
    }
}
