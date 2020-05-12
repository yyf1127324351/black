package com.back.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 可用做 部门树，菜单树，字典树等转换
 * */

@Getter
@Setter
public class TreeNode {

    private Integer id;
    private String name;
    private String code;
    private Integer type;//类型 0菜单 1按钮
    private Integer parentId;
    private Integer hasChild;
    private Integer Level; //级别
    private String url;

    private Integer sort;


    //easyui tree 必需的属性
    private String text;
    private String state;//'open' 或 'closed'，默认：'open'
    private Boolean checked;
    private String iconCls;
    private List<TreeNode> children;


    public TreeNode() {
        super();
    }
    public TreeNode(Integer id, String text) {
        super();
        this.id = id;
        this.text = text;
    }
    /**
     * 转化成easyui tree需要的树形数据（原始数据只是一个list，转化后list中的对象children属性有list）
     */
    public static List<TreeNode> convertToTreeList(List<TreeNode> nodeList) {
        List<TreeNode> treeNodeList = new ArrayList<>();
        for (TreeNode node1 : nodeList) {
            boolean mark = false;
            for (TreeNode node2 : nodeList) {
                if (node1.getParentId() != null && node1.getParentId().equals(node2.getId())) {
                    mark = true;
                    if (node2.getChildren() == null) {
                        node2.setChildren(new ArrayList<>());
                        node2.setText(node2.getText());
                        node2.setState("closed");// 让父节点显示关闭
                    }
                    node2.getChildren().add(node1);
                    break;
                }
            }
            if (!mark) {
                treeNodeList.add(node1);
            }
        }
        return treeNodeList;
    }


}
