package com.back.dao;

import com.back.model.MenuDto;
import com.back.vo.MenuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao {

    List<MenuVo> leftLevel1List(@Param("userId") Long userId);

    List<MenuVo> leftLevel2List(@Param("userId") Long userId);

    List<MenuVo> getAllMenuTree();

    Long getMenuPageCount(MenuVo menuVo);

    List<MenuVo> getMenuPageList(MenuVo menuVo);

    void insert(MenuVo menuVo);

    void updateParentMenuHasChildren(MenuVo menuVo);

    void update(MenuVo menuVo);

    List<MenuDto> getMenuByParentId(@Param("parentId") Integer parentId);

    void deleteMenu(MenuVo menuVo);
}
