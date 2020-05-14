package com.back.service.impl;

import com.back.dao.HolidayDao;
import com.back.service.HolidayService;
import com.back.vo.HolidayVo;
import com.common.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayServiceImpl implements HolidayService {

    @Autowired
    HolidayDao holidayDao;

    @Override
    public BaseResponse getHolidayPageList(HolidayVo holidayVo) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setTotal(holidayDao.getHolidayPageCount(holidayVo));
        List<HolidayVo> list = holidayDao.getHolidayPageList(holidayVo);
        baseResponse.setRows(list);
        return baseResponse;

    }
}
