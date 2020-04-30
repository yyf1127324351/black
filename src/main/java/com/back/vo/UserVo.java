package com.back.vo;

import lombok.Data;

@Data
public class UserVo {
    private Long userId;//人员ID
    private String userName;//中文名
    private String loginName;//域帐户
    private String userNo;//工号
    private String password;// 密码


    private String redirectUrl;
    private String loginTime;
    private String ip;


}
