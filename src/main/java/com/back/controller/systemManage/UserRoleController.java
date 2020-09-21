package com.back.controller.systemManage;

import com.back.controller.BaseController;
import com.back.model.UserDto;
import com.back.service.UserService;
import com.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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
    @RequestMapping(value = "/getUserRolePageList", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse getUserRolePageList(HttpServletRequest request) {
        try {
            HashMap<String, Object> map = getParametersMap(request);
            return userService.getUerRolePageList(map);
        } catch (Exception e) {
            log.error("getRolePageList:{}", e);
            e.printStackTrace();
            return BaseResponse.error();
        }

    }

    /**
     * 保存用户角色
     */
    @RequestMapping(value = "/saveUserRole", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse saveUserRole(UserDto userDto) {
        try {
            if (CollectionUtils.isEmpty(userDto.getUserRoleList())) {
                return BaseResponse.paramError("角色不能为空");
            }
            return userService.saveUserRole(userDto);
        } catch (Exception e) {
            log.error("saveUserRole:{}", e);
            e.printStackTrace();
            return BaseResponse.error();
        }

    }





}
