package com.back.service;

import com.back.model.HolidayDto;
import com.back.vo.HolidayVo;
import com.common.BaseResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface HolidayService {
    BaseResponse getHolidayPageList(HolidayVo holidayVo);

    XSSFWorkbook exportHolidayRecord(HolidayDto holidayDto);
}
