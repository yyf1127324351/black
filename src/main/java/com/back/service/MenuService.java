package com.back.service;

import com.back.vo.MenuVo;
import com.common.BaseResponse;

import java.util.List;

public interface MenuService {
    List<MenuVo> leftLevel1List();

    List<MenuVo> leftLevel2List();

    List<MenuVo> getAllMenuTree();

    BaseResponse getMenuPageList(MenuVo menuVo);

    BaseResponse addUpdateMenu(MenuVo menuVo);

    BaseResponse deleteMenu(MenuVo menuVo);
}
