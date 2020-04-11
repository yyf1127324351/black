package com.back.service.impl;

import com.back.dao.HolidayRemainDao;
import com.back.model.HolidayRemainDto;
import com.back.service.HolidayRemainService;
import com.common.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 假期余额管理实现类
 * */
@Service
public class HolidayRemainServiceImpl implements HolidayRemainService {

    @Autowired
    private HolidayRemainDao holidayRemainDao;

    @Override
    public BaseResponse getHolidayRemainList(HolidayRemainDto holidayRemainDto) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setTotal(holidayRemainDao.countHolidayRemain(holidayRemainDto));
        List<HolidayRemainDto> list = holidayRemainDao.getHolidayRemainList(holidayRemainDto);
        baseResponse.setRows(list);
        return baseResponse;
    }
}
