package com.back.controller.systemManage;

import com.back.controller.BaseController;
import com.back.model.SysConfigTypeDto;
import com.back.service.SysConfigService;
import com.back.vo.TreeNode;
import com.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @RequestMapping("/getSysConfigPageList")
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
     * 更新参数类型
     */
    @RequestMapping("/updateSysConfigType")
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
}
