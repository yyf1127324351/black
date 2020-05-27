package com.back.service.impl;

import com.back.dao.SysConfigDao;
import com.back.model.SysConfigTypeDto;
import com.back.model.SysConfigValueDto;
import com.back.service.SysConfigService;
import com.back.vo.TreeNode;
import com.common.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Autowired
    SysConfigDao sysConfigDao;

    @Override
    public List<TreeNode> getSysConfigTree() {
        List<SysConfigTypeDto> typeDtos = sysConfigDao.getAllConfigType();
        List<TreeNode> nodeList = new ArrayList<>();
        typeDtos.forEach(e ->{
            TreeNode node = new TreeNode();
//            node.setIconCls("icon-tip");
            node.setId(e.getId());
            node.setText(e.getName());
            node.setCode(e.getTypeCode());
            node.setSort(e.getSortNumber());
            nodeList.add(node);
        });

        TreeNode rootTreeNode = new TreeNode();
        rootTreeNode.setId(0L);
        rootTreeNode.setText("系统参数类型");
        rootTreeNode.setState("close");
        rootTreeNode.setHasChild(1);
        rootTreeNode.setChildren(nodeList);

        return Collections.singletonList(rootTreeNode);
    }

    @Override
    public BaseResponse getSysConfigPageList(HashMap<String, Object> map) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setTotal(sysConfigDao.getSysConfigPageCount(map));
        List<SysConfigValueDto> list = sysConfigDao.getSysConfigPageList(map);
        baseResponse.setRows(list);
        return baseResponse;
    }

    @Override
    public void updateSysConfigType(SysConfigTypeDto sysConfigTypeDto) {
        sysConfigDao.updateSysConfigType(sysConfigTypeDto);
    }

    @Override
    public void addSysConfigType(SysConfigTypeDto sysConfigTypeDto) {
        sysConfigDao.addSysConfigType(sysConfigTypeDto);
    }

    @Override
    public void addSysConfigValue(SysConfigValueDto sysConfigValueDto) {
        sysConfigDao.addSysConfigValue(sysConfigValueDto);
    }

    @Override
    public void deleteSysConfigType(Long typeId) {
        sysConfigDao.deleteSysConfigType(typeId);
        sysConfigDao.deleteSysConfigValueByTypeId(typeId);
    }

    @Override
    public void updateSysConfigValue(SysConfigValueDto sysConfigValueDto) {
        sysConfigDao.updateSysConfigValue(sysConfigValueDto);
    }
}
