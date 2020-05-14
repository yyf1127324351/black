package com.back.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HolidayVo extends BaseVo {
    private Long id;
    private Long staffId;
    private String staffName;
    private Integer holidayType;
    private BigDecimal hours;
    private String startTime;
    private String endTime;
    private String reason;
    private String remark;
    private Integer systemSource;
    private Long oaId;
    private Integer status;

}
