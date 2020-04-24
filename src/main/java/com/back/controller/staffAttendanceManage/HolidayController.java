package com.back.controller.staffAttendanceManage;

import com.back.model.Holiday;
import com.back.service.HolidayService;
import com.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/holiday")
@Slf4j
public class HolidayController {
    @Autowired
    HolidayService holidayService;

    /**
     * 转跳请假管理页面
     * */
    @RequestMapping("/goHolidayPage")
    public ModelAndView goHolidayPage(){
        ModelAndView mv = new ModelAndView("/staffAttendanceManage/holidayPage");
        return mv;
    }

    /**
     * 获取请假记录列表
     * */
    @RequestMapping("/getHolidayPageList")
    @ResponseBody
    public BaseResponse getHolidayPageList(Holiday holiday){
        //分页参数
        try {
            return holidayService.getHolidayPageList(holiday);
        }catch (Exception e){
            log.error("holidayRemain/holidayRemainList-exception:{}",e);
            return BaseResponse.error();
        }

    }


}
