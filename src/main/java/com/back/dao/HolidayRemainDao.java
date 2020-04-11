package com.back.dao;

import com.back.model.HolidayRemainDto;

import java.util.List;

public interface HolidayRemainDao {
    Long countHolidayRemain(HolidayRemainDto holidayRemainDto);

    List<HolidayRemainDto> getHolidayRemainList(HolidayRemainDto holidayRemainDto);
}
