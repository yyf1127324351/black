package com.back.controller.systemManage;

import com.back.model.TreeNode;
import com.back.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    MenuService menuService;

    @RequestMapping("/goMenu")
    public ModelAndView goMenu(){
        return new ModelAndView("/systemManage/menuPage");
    }

    @RequestMapping("/getAllMenuTree")
    @ResponseBody
    public List<TreeNode> getAllMenuTree(){
        return  menuService.getAllMenuTree();
    }

}
