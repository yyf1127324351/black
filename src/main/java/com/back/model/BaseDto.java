package com.back.model;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class BaseDto {

    /*数据库表基本字段*/
    private Integer status;
    private String remark;
    private Integer deleted;
    private Long createUser;
    private String createUserName;
    private String createTime;
    private Long updateUser;
    private String updateUserName;
    private String updateTime;

    /*登陆人权限*/

}
