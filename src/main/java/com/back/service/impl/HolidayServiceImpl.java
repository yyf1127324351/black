package com.back.service.impl;

import com.back.dao.HolidayDao;
import com.back.model.HolidayDto;
import com.back.service.HolidayService;
import com.back.vo.HolidayVo;
import com.common.BaseResponse;
import com.constant.SourceEnum;
import com.utils.ExportExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
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

    @Override
    public XSSFWorkbook exportHolidayRecord(HolidayDto holidayDto) {

        List<HolidayDto> holidayList = holidayDao.getHolidayList(holidayDto);
        String sheetName = "请假记录";
        String title = "工号,姓名,请假类型,请假时长(小时),请假开始时间,请假结束时间,请假原因,状态,数据来源,OA单号";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = ExportExcelUtils.initWorkBookSheet(workbook,sheetName,title);

        int index = 1;
        if (CollectionUtils.isNotEmpty(holidayList)) {
            for (HolidayDto holiday : holidayList) {
                int cellNum = 0;
                XSSFRow row1 = sheet.createRow(index);
                cellNum = ExportExcelUtils.buildCellNew(row1, cellNum, holiday.getStaffId());
                cellNum = ExportExcelUtils.buildCellNew(row1, cellNum, holiday.getStaffName());
                cellNum = ExportExcelUtils.buildCellNew(row1, cellNum, holiday.getHolidayType());
                cellNum = ExportExcelUtils.buildCellNew(row1, cellNum, holiday.getHours().stripTrailingZeros().toPlainString());
                cellNum = ExportExcelUtils.buildCellNew(row1, cellNum, holiday.getStartTime());
                cellNum = ExportExcelUtils.buildCellNew(row1, cellNum, holiday.getEndTime());
                cellNum = ExportExcelUtils.buildCellNew(row1, cellNum, holiday.getReason());
                cellNum = ExportExcelUtils.buildCellNew(row1, cellNum, holiday.getStatus());
                cellNum = ExportExcelUtils.buildCellNew(row1, cellNum, SourceEnum.getValue(holiday.getSystemSource()));
                cellNum = ExportExcelUtils.buildCellNew(row1, cellNum, holiday.getOaId());
                index++;
            }
        }
        return workbook;
    }
}
