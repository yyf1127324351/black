package com.back.dao;

import com.back.model.SysConfigTypeDto;
import com.back.model.SysConfigValueDto;

import java.util.HashMap;
import java.util.List;

public interface SysConfigDao {
    List<SysConfigTypeDto> getAllConfigType();

    Long getSysConfigPageCount(HashMap<String, Object> map);

    List<SysConfigValueDto> getSysConfigPageList(HashMap<String, Object> map);

    void updateSysConfigType();
}
