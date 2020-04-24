package com.back.service;

import com.back.model.Holiday;
import com.common.BaseResponse;

public interface HolidayService {
    BaseResponse getHolidayPageList(Holiday holiday);
}
