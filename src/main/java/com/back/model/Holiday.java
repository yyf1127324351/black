package com.back.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 *  员工请假记录实体类
 * */
@Data
public class Holiday extends BaseModel {
    private Long id;
    private Long staffId;
    private String staffName;
    private Integer holidayType;
    private BigDecimal hours;
    private String startTime;
    private String endTime;
    private Integer year;
    private String reason;
    private String remark;
    private Integer systemSource;
    private Long oaId;
    private Integer status;

}
