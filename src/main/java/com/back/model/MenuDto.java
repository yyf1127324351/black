package com.back.model;

import lombok.Data;
/**
 * 菜单表实体类
 * menu表
 * */
@Data
public class MenuDto extends BaseDto {
    private Integer id;
    private String name;
    private String code;
    private Integer type;//类型 0菜单 1功能点
    private Integer parentId;
    private Integer level;
    private String url;
    private Integer hasChild;
    private Integer sortNumber;
    private Integer deleted;
}
