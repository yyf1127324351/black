package com.back.dao;

import com.back.model.RoleAuthorityDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleAuthorityDao {
    List<RoleAuthorityDto> getMenuIdsByRoleId(@Param("roleId") Long roleId);
}
