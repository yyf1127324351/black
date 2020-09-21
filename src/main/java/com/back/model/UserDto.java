package com.back.model;

import lombok.Data;

import java.util.List;

@Data
public class UserDto extends BaseDto{
    private Long id;
    private Long userId;
    private Long userNo;
    private String loginName;//员工账号（登录账号）
    private String userName;//员工姓名
    private String password;// 密码
    private String encPassword;// 密文密码

    private String userRoleName;
    private String userRoleIds;
    private List<String> userRoleList;

}
