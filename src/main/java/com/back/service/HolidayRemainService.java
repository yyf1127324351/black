package com.back.service;

import com.back.model.HolidayRemainModel;
import com.common.BaseResponse;

public interface HolidayRemainService {
    BaseResponse getHolidayRemainList(HolidayRemainModel holidayRemainDto);
}
