package com.back.controller.systemManage;

import com.back.model.Menu;
import com.back.model.TreeNode;
import com.back.service.MenuService;
import com.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/menu")
@Slf4j
public class MenuController {
    @Autowired
    MenuService menuService;

    @RequestMapping("/goMenuPage")
    public ModelAndView goMenuPage(){
        return new ModelAndView("/systemManage/menuPage");
    }

    @RequestMapping("/getAllMenuTree")
    @ResponseBody
    public List<Menu> getAllMenuTree(){
        return  menuService.getAllMenuTree();
    }

    /**
     * 获取菜单列表
     * */
    @RequestMapping("/getMenuPageList")
    @ResponseBody
    public BaseResponse getMenuPageList(Menu menu){
        //分页参数
        try {
            return menuService.getMenuPageList(menu);
        }catch (Exception e){
            log.error("getMenuPageListException:{}",e);
            return BaseResponse.error();
        }

    }

}
