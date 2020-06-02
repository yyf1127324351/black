package com.back.controller.systemManage;

import com.back.controller.BaseController;
import com.back.model.RoleDto;
import com.back.service.RoleService;
import com.common.BaseResponse;
import com.common.session.SessionContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
@Slf4j
public class RoleController extends BaseController {

    @Autowired
    RoleService roleService;

    /**
     * 转跳系统参数配置页面
     */
    @RequestMapping("/goRolePage")
    public ModelAndView goRolePage() {
        return new ModelAndView("/systemManage/rolePage");
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/getRolePageList", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse getRolePageList(HttpServletRequest request) {
        try {
            HashMap<String, Object> map = getParametersMap(request);
            return roleService.getRolePageList(map);
        } catch (Exception e) {
            log.error("getRolePageList:{}", e);
            return BaseResponse.error();
        }

    }

    /**
     * 添加角色
     */
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse addRole(RoleDto roleDto) {
        try {
            roleService.addRole(roleDto);
            return BaseResponse.success();
        } catch (Exception e) {
            log.error("addRole:{}", e);
            return BaseResponse.error();
        }

    }

    /**
     * 更新角色
     */
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse updateRole(RoleDto roleDto) {
        try {
            roleService.updateRole(roleDto);
            return BaseResponse.success();
        } catch (Exception e) {
            log.error("updateRoleStatus:{}", e);
            return BaseResponse.error();
        }

    }

    /**
     * 获取所有的权限树
     */
    @RequestMapping(value = "/getAuthTree", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse getAuthTree(Long roleId) {
        try {
            Map<String, List> treeMap = roleService.getAuthTree(roleId);
            return BaseResponse.success(treeMap);
        } catch (Exception e) {
            log.error("getAuthTree:{}", e);
//            e.printStackTrace();
            return BaseResponse.error();
        }

    }



}
