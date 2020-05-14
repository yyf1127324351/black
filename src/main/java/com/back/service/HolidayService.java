package com.back.service;

import com.back.vo.HolidayVo;
import com.common.BaseResponse;

public interface HolidayService {
    BaseResponse getHolidayPageList(HolidayVo holidayVo);
}
