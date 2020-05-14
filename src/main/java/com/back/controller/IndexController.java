package com.back.controller;

import com.back.vo.MenuVo;
import com.back.service.MenuService;
import com.common.session.SessionContainer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private MenuService menuService;


    @RequestMapping("/goIndex")
    public ModelAndView goHr() {
        ModelAndView mv = new ModelAndView("index/index");

        // 顶级菜单权限控制
        List<MenuVo> level1List = menuService.leftLevel1List();
        List<MenuVo> level2List = menuService.leftLevel2List();
        if (CollectionUtils.isNotEmpty(level2List)) {
            for (MenuVo menuVo1 : level1List) {
                List<MenuVo> children = new ArrayList<>();
                for (MenuVo p2 : level2List) {
                    if (menuVo1.getId().toString().equals(p2.getParentId().toString())) {
                        children.add(p2);
                    }
                }
                menuVo1.setChildren(children);
            }
        }

        mv.addObject("level1List", level1List); // 检化验数据管理
        mv.addObject("curUserName", SessionContainer.getUserName());
        mv.addObject("userId", SessionContainer.getUserId());
//        mv.addObject("curUserName", "黄慧颖");
//        mv.addObject("userId", "1");
        return mv;
    }
}
