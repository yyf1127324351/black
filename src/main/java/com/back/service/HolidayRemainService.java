package com.back.service;

import com.back.model.HolidayRemainDto;
import com.common.BaseResponse;

public interface HolidayRemainService {
    BaseResponse getHolidayRemainList(HolidayRemainDto holidayRemainDto);
}
