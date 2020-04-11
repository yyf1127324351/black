package com.back.dao;

import com.back.model.Menu;

import java.util.List;

public interface MenuDao {

    List<Menu> leftLevel1List(String operatorNo);

    List<Menu> leftLevel2List(String operatorNo);

    List<Menu> getAllMenuTree();
}
