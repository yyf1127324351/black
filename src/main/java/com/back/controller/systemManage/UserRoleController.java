package com.back.controller.systemManage;

import com.back.controller.BaseController;
import com.back.service.UserService;
import com.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/userRole")
@Slf4j
public class UserRoleController extends BaseController{

    @Autowired
    private UserService userService;

    /**
     * 转跳用户角色管理页面
     */
    @RequestMapping("/goUserRolePage")
    public ModelAndView goRolePage() {
        return new ModelAndView("/systemManage/userRolePage");
    }


    /**
     * 获取用户角色列表
     */
    @RequestMapping(value = "/getUerRolePageList", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse getUerRolePageList(HttpServletRequest request) {
        try {
            HashMap<String, Object> map = getParametersMap(request);
            return userService.getUerRolePageList(map);
        } catch (Exception e) {
            log.error("getRolePageList:{}", e);
            e.printStackTrace();
            return BaseResponse.error();
        }

    }
}
