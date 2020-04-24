package com.back.service.impl;

import com.back.dao.HolidayDao;
import com.back.model.Holiday;
import com.back.service.HolidayService;
import com.common.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayServiceImpl implements HolidayService {

    @Autowired
    HolidayDao holidayDao;

    @Override
    public BaseResponse getHolidayPageList(Holiday holiday) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setTotal(holidayDao.getHolidayPageCount(holiday));
        List<Holiday> list = holidayDao.getHolidayPageList(holiday);
        baseResponse.setRows(list);
        return baseResponse;

    }
}