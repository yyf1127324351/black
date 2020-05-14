package com.back.dao;

import com.back.model.HolidayRemainModel;

import java.util.List;

public interface HolidayRemainDao {
    Long countHolidayRemain(HolidayRemainModel holidayRemainDto);

    List<HolidayRemainModel> getHolidayRemainList(HolidayRemainModel holidayRemainDto);
}
