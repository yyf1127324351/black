package com.back.dao;

import com.back.vo.HolidayVo;

import java.util.List;

public interface HolidayDao {
    List<HolidayVo> getHolidayPageList(HolidayVo holiday);

    Long getHolidayPageCount(HolidayVo holiday);
}
