package com.back.service.impl;

import com.back.dao.MenuDao;
import com.back.model.Menu;
import com.back.model.TreeNode;
import com.back.service.MenuService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 系统菜单service实现类
 * */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;


    @Override
    public List<Menu> leftLevel1List() {

        List<Menu> left = menuDao.leftLevel1List("00001");
        List<Menu> tree = Menu.convertToTreeDataByParentId(left);
        this.sortPhFuntionList(tree);
        return tree;


    }

    @Override
    public List<Menu> leftLevel2List() {
        List<Menu> left = menuDao.leftLevel2List("00001");
        List<Menu> tree = Menu.convertToTreeDataByParentId(left);
        this.sortPhFuntionList(tree);
        return tree;
    }

    @Override
    public List<TreeNode> getAllMenuTree() {
        //查询出所有的菜单
        List<Menu>  menuList =  menuDao.getAllMenuTree();

        List<TreeNode> nodeList = new ArrayList<>();
        menuList.forEach(e->{
            TreeNode node = new TreeNode();
            node.setId(e.getId());
            node.setText(e.getName());
            node.setParentId(e.getParentId());
            node.setHasChild(e.getHasChild());
            nodeList.add(node);
        });
        //转成所有1级菜单树
        List<TreeNode> treeNodeList = TreeNode.convertToTreeList(nodeList);

        TreeNode rootTreeNode = new TreeNode();
        rootTreeNode.setId(0);
        rootTreeNode.setState("close");
        rootTreeNode.setText("菜单");
        rootTreeNode.setHasChild(1);
        rootTreeNode.setChildren(treeNodeList);

        List<TreeNode> list = new ArrayList<>();
        list.add(rootTreeNode);
        return list;





    }

    // 排序
    private void sortPhFuntionList(List<Menu> tree) {
        Collections.sort(tree, new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                if (o1.getSort() == null && o2.getSort() == null) {
                    return 0;
                }
                if (o1.getSort() == null) {
                    return 1;
                }
                if (o2.getSort() == null) {
                    return -1;
                }
                return o1.getSort().compareTo(o2.getSort());
            }
        });
        for (Menu pf : tree) {
            if (CollectionUtils.isNotEmpty(pf.getChildren())) {
                this.sortPhFuntionList(pf.getChildren());
            }
        }
    }
}
