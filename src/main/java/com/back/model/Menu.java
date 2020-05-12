package com.back.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Menu {

	private Integer id;
	private String name;
	private String code;
	private Integer type;//类型 0菜单 1功能点
	private Integer parentId;
	private Integer level;
	private String url;
	private Integer hasChild;
	private Integer sort;
	
	//easyui tree 必需的属性
	private String text;
	private String state;//'open' 或 'closed'，默认：'open'
	private Boolean checked;
	private String iconCls;
	private List<Menu> children;
	
	
	/**
	 * 转化成easyui tree需要的树形数据
	 */
	public static List<Menu> convertToTreeDataByParentId(List<Menu> tdList) {
		List<Menu> nodeList = new ArrayList<>();
		for(Menu node1 : tdList){
			node1.setText(node1.getName());//填充tree显示文本
			if(node1.getType() == 1) {//功能点特殊图标
				node1.setIconCls("icon-tip");
			}
		    boolean mark = false;  
		    for(Menu node2 : tdList){
		        if(node1.getParentId()!=null && node1.getParentId().equals(node2.getId())){  
		            mark = true;  
		            if(node2.getChildren() == null) {
		            	node2.setChildren(new ArrayList<>());
		            	//node2.setState("closed");//让父节点显示关闭
		            }
		            node2.getChildren().add(node1);   
		            break;  
		        }  
		    }  
		    if(!mark){  
		        nodeList.add(node1);   
		    }  
		}
		return nodeList;
	}
	//填充是否有权限
	public static void fillChecked(List<Menu> allFunctions, List<Long> ownFunctionIdList) {
		for (Menu pf : allFunctions) {
			if(ownFunctionIdList.contains(pf.getId())) {
				pf.setChecked(true);
			}
		}
	}
	
	/**
	 * 生成一个tree，最外面再包一层
	 */
	public static Menu genRootTree(Integer id, String text, List<Menu> phList) {
		Menu portalRoot = new Menu(id, text);
		portalRoot.setChildren(Menu.convertToTreeDataByParentId(phList));
		return portalRoot;
	}
	
	public Menu(Integer id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public Menu() {
	}
}
