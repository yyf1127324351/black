package com.back.service.impl;

import com.back.dao.MenuDao;
import com.back.model.Menu;
import com.back.model.TreeNode;
import com.back.service.MenuService;
import com.common.BaseResponse;
import com.common.session.SessionContainer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Comparator.comparing;

/**
 * 系统菜单service实现类
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;


    @Override
    public List<Menu> leftLevel1List() {
        //查出一级菜单
        List<Menu> left = menuDao.leftLevel1List(SessionContainer.getUserId());
        // 转化成easyui tree需要的树形数据
        List<Menu> treeList = Menu.convertToTreeDataByParentId(left);
        this.sortMenuList(treeList);
        return treeList;
    }

    @Override
    public List<Menu> leftLevel2List() {
        //查出二级菜单
        List<Menu> left = menuDao.leftLevel2List(SessionContainer.getUserId());
        // 转化成easyui tree需要的树形数据
        List<Menu> treeList = Menu.convertToTreeDataByParentId(left);
        this.sortMenuList(treeList);
        return treeList;
    }

    @Override
    public List<Menu> getAllMenuTree() {
        List<Menu> list = new ArrayList<>();
        //查询出所有的菜单
        List<Menu> menuList = menuDao.getAllMenuTree();
        if (CollectionUtils.isNotEmpty(menuList)) {
            List<Menu> newMenuList = Menu.convertToTreeDataByParentId(menuList);
            Menu menu = new Menu();
            menu.setId(0);
            menu.setState("close");
            menu.setText("菜单");
            menu.setHasChild(1);
            menu.setChildren(newMenuList);
            list.add(menu);
        }
        return list;



//        List<TreeNode> nodeList = new ArrayList<>();
//        menuList.forEach(e -> {
//            TreeNode node = new TreeNode();
//            node.setId(e.getId());
//            node.setText(e.getName());
//            node.setParentId(e.getParentId());
//            node.setLevel(node.getLevel());
//            node.setHasChild(e.getHasChild());
//            nodeList.add(node);
//        });
//        //转成所有1级菜单树
//        List<TreeNode> treeNodeList = TreeNode.convertToTreeList(nodeList);
//
//        TreeNode rootTreeNode = new TreeNode();
//        rootTreeNode.setId(0);
//        rootTreeNode.setState("close");
//        rootTreeNode.setText("菜单");
//        rootTreeNode.setHasChild(1);
//        rootTreeNode.setChildren(treeNodeList);
//
//        List<TreeNode> list = new ArrayList<>();
//        list.add(rootTreeNode);


    }

    @Override
    public BaseResponse getMenuPageList(Menu menu) {
        BaseResponse baseResponse = new BaseResponse();
        if (StringUtils.isNotBlank(menu.getMenuIds())) {
            List<String> ids = Arrays.asList(menu.getMenuIds().split(","));
            menu.setIds(ids);
        }
        baseResponse.setTotal(menuDao.getMenuPageCount(menu));
        List<Menu> list = menuDao.getMenuPageList(menu);
        baseResponse.setRows(list);
        return baseResponse;
    }

    // 排序
    private void sortMenuList(List<Menu> list) {
        list.sort(comparing(Menu::getSortNumber));
        for (Menu menu : list) {
            if (CollectionUtils.isNotEmpty(menu.getChildren())) {
                this.sortMenuList(menu.getChildren());
            }
        }
    }

}
