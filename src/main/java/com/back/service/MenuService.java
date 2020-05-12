package com.back.service;

import com.back.model.Menu;
import com.back.model.TreeNode;
import com.common.BaseResponse;

import java.util.List;

public interface MenuService {
    List<Menu> leftLevel1List();

    List<Menu> leftLevel2List();

    List<TreeNode> getAllMenuTree();

    BaseResponse getMenuPageList(Menu menu);
}
