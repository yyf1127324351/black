package com.back.service.impl;

import com.back.dao.MenuDao;
import com.back.dao.RoleAuthorityDao;
import com.back.dao.RoleDao;
import com.back.dao.SysConfigDao;
import com.back.model.RoleAuthorityDto;
import com.back.model.RoleDto;
import com.back.service.RoleService;
import com.back.vo.TreeNode;
import com.common.BaseResponse;
import com.common.session.SessionContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleDao roleDao;
    @Autowired
    MenuDao menuDao;
    @Autowired
    RoleAuthorityDao roleAuthorityDao;
    @Autowired
    SysConfigDao sysConfigDao;

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

    @Override
    public Map<String, List> getAuthTree(Long roleId) {

        //查出该角色有权限的菜单，部门，地区，
        List<RoleAuthorityDto> allAuthIds = roleAuthorityDao.getMenuIdsByRoleId(roleId);

        //1.菜单权限
        //查出所有的菜单
        List<TreeNode> allMenuList = menuDao.getAllMenuTreeNode();
        //角色有权限的菜单
        List<RoleAuthorityDto> menuAuthList = allAuthIds.stream().filter(e -> e.getType() == 1).collect(Collectors.toList());
        List<Long> menuIds = menuAuthList.stream().map(RoleAuthorityDto::getAuthId).collect(Collectors.toList());
        TreeNode.isChecked(allMenuList, menuIds); //选中有权限的菜单
        List<TreeNode> menuTreeList = TreeNode.convertToTreeList(allMenuList,"open");
        TreeNode.sortTreeNode(menuTreeList); //菜单排序
        //2.部门权限

        //3.地区权限
        //查出所有的地区
        List<TreeNode> allAreaList = sysConfigDao.getAllAreaTreeNode();
        //角色有权限的地区
        List<RoleAuthorityDto> areaAuthList = allAuthIds.stream().filter(e -> e.getType() == 3).collect(Collectors.toList());
        List<Long> areaIds = areaAuthList.stream().map(RoleAuthorityDto::getAuthId).collect(Collectors.toList());
        TreeNode.isChecked(allAreaList, areaIds);
        //插入所有部门root节点
        TreeNode rootArea = new TreeNode(0L, "所有地区");
        int checkedCount = (int) allAreaList.stream().filter(e -> e.getChecked() == true).count();
        if (checkedCount == allAreaList.size()) {
            rootArea.setChecked(true);
        }
        allAreaList.add(rootArea);

        List<TreeNode> areaTreeList = TreeNode.convertToTreeList(allAreaList,"open");

        Map<String, List> treeMap = new HashMap<>();
        treeMap.put("menuTreeData", menuTreeList);
        treeMap.put("areaTreeData", areaTreeList);

        return treeMap;
    }
}
