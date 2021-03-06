package com.back.controller.systemManage;

import com.back.vo.MenuVo;
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
    public List<MenuVo> getAllMenuTree(){
        return  menuService.getAllMenuTree();
    }

    /**
     * 获取菜单列表
     * */
    @RequestMapping("/getMenuPageList")
    @ResponseBody
    public BaseResponse getMenuPageList(MenuVo menuVo){
        try {
            return menuService.getMenuPageList(menuVo);
        }catch (Exception e){
            log.error("getMenuPageListException:{}",e.getMessage());
            return BaseResponse.error();
        }

    }
    /**
     * 添加或更新菜单
     * */
    @RequestMapping("/addUpdateMenu")
    @ResponseBody
    public BaseResponse addUpdateMenu(MenuVo menuVo){
        try {
            return menuService.addUpdateMenu(menuVo);
        }catch (Exception e){
            log.error("addUpdateMenuException:{}",e.getMessage());
            return BaseResponse.error();
        }
    }
    /**
     * 删除菜单
     * */
    @RequestMapping("/deleteMenu")
    @ResponseBody
    public BaseResponse deleteMenu(MenuVo menuVo){
        try {
            return menuService.deleteMenu(menuVo);
        }catch (Exception e){
            log.error("deleteMenuException:{}",e.getMessage());
            return BaseResponse.error();
        }
    }



}
