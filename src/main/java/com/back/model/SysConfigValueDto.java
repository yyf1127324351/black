package com.back.model;

import lombok.Data;

/**
 * 系统参数实体类
 * sys_config_value表
 */
@Data
public class SysConfigValueDto extends BaseDto{
    private Long id;
    private Long typeId; //sys_config_type 表id
    private String paramKey; //参数键
    private String paramValue; //参数值
    private String describe; //描述
    private String remark;//备注

    //sys_config_type 表 name
    private String typeName; //参数类型
    //sys_config_type 表 type_code
    private String typeCode; //参数类型编码


}