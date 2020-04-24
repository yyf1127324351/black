package com.back.controller.staffAttendanceManage;

import com.back.model.HolidayRemainDto;
import com.back.service.HolidayRemainService;
import com.common.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
/**
 * 假期余额管理controller
 * */
@Controller
@RequestMapping("/holidayRemain")
public class HolidayRemainController {
    private static Logger logger = LoggerFactory.getLogger(HolidayRemainController.class);

    @Autowired
    private HolidayRemainService holidayRemainService;

    /**
     * 转跳至假期余额管理页面
     * */
    @RequestMapping("/goRemainManage")
    public ModelAndView goRemainManage(){
        ModelAndView mv = new ModelAndView("/staffAttendanceManage/holidayRemainPage");
        return mv;
    }

    /**
     * 获取假期余额列表
     * */
    @RequestMapping("/holidayRemainList")
    @ResponseBody
    public BaseResponse holidayRemainList(HolidayRemainDto holidayRemainDto){
        //分页参数
        try {
            return holidayRemainService.getHolidayRemainList(holidayRemainDto);
        }catch (Exception e){
            logger.error("holiday/holidayRemainList-exception:{}",e);
            return BaseResponse.error();
        }

    }
}
