package com.back.controller.systemManage;

import com.back.controller.BaseController;
import com.back.model.SysConfigTypeDto;
import com.back.model.SysConfigValueDto;
import com.back.service.SysConfigService;
import com.back.vo.TreeNode;
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
import java.util.List;

@Controller
@RequestMapping("/sysConfig")
@Slf4j
public class SysConfigController extends BaseController {
    @Autowired
    SysConfigService sysConfigService;


    /**
     * 转跳系统参数配置页面
     */
    @RequestMapping("/goSysConfigPage")
    public ModelAndView goSysConfigPage() {
        return new ModelAndView("/systemManage/sysConfigPage");
    }

    @RequestMapping("/getSysConfigTree")
    @ResponseBody
    public List<TreeNode> getSysConfigTree() {
        return sysConfigService.getSysConfigTree();
    }

    /**
     * 获取系统参数（数据字典）列表
     */
    @RequestMapping(value = "/getSysConfigPageList",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse getSysConfigPageList(HttpServletRequest request) {
        try {
            HashMap<String, Object> map = getParametersMap(request);
            return sysConfigService.getSysConfigPageList(map);
        } catch (Exception e) {
            log.error("getSysConfigPageList:{}", e.getMessage());
            return BaseResponse.error();
        }

    }
    /**
     * 新增参数类型
     */
    @RequestMapping(value = "/addSysConfigType",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse addSysConfigType(SysConfigTypeDto paramModel) {
        try {
            sysConfigService.addSysConfigType(paramModel);
            return BaseResponse.success();
        } catch (Exception e) {
            log.error("addSysConfigType:{}", e.getMessage());
            return BaseResponse.error();
        }
    }
    /**
     * 更新参数类型
     */
    @RequestMapping(value ="/updateSysConfigType", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse updateSysConfigType(SysConfigTypeDto paramModel) {
        try {
            sysConfigService.updateSysConfigType(paramModel);
            return BaseResponse.success();
        } catch (Exception e) {
            log.error("updateSysConfigType:{}", e.getMessage());
            return BaseResponse.error();
        }
    }

    /**
     * 删除参数类型
     */
    @RequestMapping(value = "/deleteSysConfigType", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse deleteSysConfigType(Long typeId) {
        try {
            sysConfigService.deleteSysConfigType(typeId);
            return BaseResponse.success();
        } catch (Exception e) {
            log.error("deleteSysConfigType:{}", e.getMessage());
            return BaseResponse.error();
        }
    }



    /**
     * 新增参数值
     */
    @RequestMapping(value = "/addSysConfigValue",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse addSysConfigValue(SysConfigValueDto sysConfigValueDto) {
        try {
            sysConfigService.addSysConfigValue(sysConfigValueDto);
            return BaseResponse.success();
        } catch (Exception e) {
            log.error("addSysConfigValue:{}", e.getMessage());
            return BaseResponse.error();
        }
    }
    /**
     * 更新参数值/更新参数值状态（启用，停用）
     */
    @RequestMapping(value = "/updateSysConfigValue",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse updateSysConfigValue(SysConfigValueDto sysConfigValueDto) {
        try {
            sysConfigService.updateSysConfigValue(sysConfigValueDto);
            return BaseResponse.success();
        } catch (Exception e) {
            log.error("updateSysConfigValue:{}", e.getMessage());
            return BaseResponse.error();
        }
    }

}
