package com.back.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 *  员工可用假期天数实体类
 * */
@Data
public class HolidayRemainDto extends BaseDto {
    private Long id;
    private String operatorNo; // 员工工号
    private String operatorName; //员工姓名

    private Integer year;// 年份

    //年假
    private BigDecimal totalYearlyHoliday;// 总年假时长(单位：天)

    private BigDecimal remainYearlyHoliday;// 剩余年假时长(单位：天)

    private BigDecimal usedYearlyHoliday;//已用年假(单位：天)

    private BigDecimal lastyearYearlyHoliday;//上年结转年假(单位：天)

    private BigDecimal calculateYearlyHoliday;//当年年假(单位：天)

    private BigDecimal calculateCompanyYearlyHoliday;//当年公司年假(单位：天)

    private BigDecimal adjustmentYearlyHoliday;//年假调整(单位：天)

    //调休假
    private BigDecimal totalExchangeHoliday; // 总调休假期时长(单位：小时)

    private BigDecimal remainExchangeHoliday; // 剩余调休假期时长(单位：小时)

    private BigDecimal usedExchangeHoliday;//已用调休假期时长(单位：小时)

    private BigDecimal adjustmentExchangeHoliday;//调休假调整(单位：小时)

    private BigDecimal lastyearExchangeHoliday;//上年转接调休假(单位：小时)

    private BigDecimal calculateExchangeHoliday;//当年调休假

    private String creator; //创建人

    private String updater; //更新人

    private String createDate;

    private String updateDate;

    private String operatorJob;//员工岗位id
    private String operatorJobName;//员工岗位名称
    private String departmentId;//员工部门id
    private String departmentName;//员工部门名称
    private String deptPath;//部门路径


}
