package com.back.service;

import com.back.model.SysConfigTypeDto;
import com.back.vo.TreeNode;
import com.common.BaseResponse;

import java.util.HashMap;
import java.util.List;

public interface SysConfigService {
    List<TreeNode> getSysConfigTree();

    BaseResponse getSysConfigPageList(HashMap<String, Object> map);

    void updateSysConfigType(SysConfigTypeDto sysConfigTypeDto);

    void addSysConfigType(SysConfigTypeDto sysConfigTypeDto);
}
