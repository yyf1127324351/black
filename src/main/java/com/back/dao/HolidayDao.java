package com.back.dao;

import com.back.model.Holiday;

import java.util.List;

public interface HolidayDao {
    List<Holiday> getHolidayPageList(Holiday holiday);

    Long getHolidayPageCount(Holiday holiday);
}
