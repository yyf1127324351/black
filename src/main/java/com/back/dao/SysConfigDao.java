package com.back.dao;

import com.back.model.SysConfigTypeDto;
import com.back.model.SysConfigValueDto;
import com.back.vo.TreeNode;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface SysConfigDao {
    List<SysConfigTypeDto> getAllConfigType();

    Long getSysConfigPageCount(HashMap<String, Object> map);

    List<SysConfigValueDto> getSysConfigPageList(HashMap<String, Object> map);

    void updateSysConfigType(SysConfigTypeDto paramModel);

    void addSysConfigType(SysConfigTypeDto sysConfigTypeDto);

    void addSysConfigValue(SysConfigValueDto sysConfigValueDto);

    void deleteSysConfigType(@Param("typeId") Long typeId);

    void deleteSysConfigValueByTypeId(@Param("typeId") Long typeId);

    void updateSysConfigValue(SysConfigValueDto sysConfigValueDto);

    List<TreeNode> getAllAreaTreeNode();
}
