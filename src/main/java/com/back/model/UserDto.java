package com.back.model;

import lombok.Data;

@Data
public class UserDto {
    private Long userId;
    private String userNo;//员工工号
    private String loginName;//员工账号（登录账号）
    private String userName;//员工姓名
    private String password;// 密码
    private String encPassword;// 密文密码

}
