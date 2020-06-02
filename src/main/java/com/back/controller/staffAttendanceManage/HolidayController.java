package com.back.controller.staffAttendanceManage;

import com.back.model.HolidayDto;
import com.back.service.HolidayService;
import com.back.vo.HolidayVo;
import com.common.BaseResponse;
import com.utils.ExportExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

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
        return new ModelAndView("/staffAttendanceManage/holidayPage");
    }

    /**
     * 获取请假记录列表
     * */
    @RequestMapping("/getHolidayPageList")
    @ResponseBody
    public BaseResponse getHolidayPageList(HolidayVo holidayVo){
        //分页参数
        try {
            return holidayService.getHolidayPageList(holidayVo);
        }catch (Exception e){
            log.error("getHolidayPageListException:{}",e);
            return BaseResponse.error();
        }

    }
    /**
     * 导出请假记录列表
     * */
    @RequestMapping("/exportHolidayRecord")
    public void exportHolidayRecord(HttpServletResponse response, HolidayDto holidayDto){
        try {
            XSSFWorkbook workbook =holidayService.exportHolidayRecord(holidayDto);
            String fileName = "请假记录";
            ExportExcelUtils.exportXlsxFile(response,workbook, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getHolidayPageListException:{}",e);
        }

    }


}
