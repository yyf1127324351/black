package com.back.dao;

import com.back.model.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao {

    List<Menu> leftLevel1List(@Param("userId") Long userId);

    List<Menu> leftLevel2List(@Param("userId") Long userId);

    List<Menu> getAllMenuTree();

    Long getMenuPageCount(Menu menu);

    List<Menu> getMenuPageList(Menu menu);

    void insert(Menu menu);

    void updateParentMenuHasChildren(Menu menu);

    void update(Menu menu);
}
