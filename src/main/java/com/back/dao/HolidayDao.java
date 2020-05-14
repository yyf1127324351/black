package com.back.dao;

import com.back.model.HolidayDto;
import com.back.vo.HolidayVo;

import java.util.List;

public interface HolidayDao {
    List<HolidayVo> getHolidayPageList(HolidayVo holiday);

    Long getHolidayPageCount(HolidayVo holiday);

    List<HolidayDto> getHolidayList(HolidayDto holidayDto);
}
