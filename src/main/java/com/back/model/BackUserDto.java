package com.back.model;

import lombok.Data;

import java.util.List;
@Data
public class BackUserDto extends BaseDto {
	private Long userId;
	private String userNo;//员工工号
	private String loginName;//员工账号（登录账号）
	private String userName;//员工姓名
	private String password;// 密码
	private String encPassword;// 密文密码
	private String email;//员工邮箱

    private String redirectUrl;

	private Integer operatorStatus;//状态
	private String operatorSerialNum;//员工编号
	private String sex;//员工性别(1：男2：女)
	private String comp;//员工所属公司
	private String deptAll;//员工部门全路径
	private Long deptID;//所在部门ID
	private Long operatorOnDeptId;
	private String deptName;//所在部门名称
	private Long leaderId;//上级id
	private String leaderNo;//上级领导工号
	private String leaderName;//上级领导名称
	private Integer isAdmin;//是否超级管理员
	private Integer deptGroup;
	private Boolean isErpUser;
	private String code;//ERP的用户ID
	private String ssoRedirectUrl;
	
	private List<Long> userFunctionAdd;
	private List<Long> userFunctionDeduct;
	
	private String systemAlias;//系统别名
	
	private Integer start;
	private Integer limit;

	private String isProd;
	
	private String orgTitlePkid;
	
	
	private String resourceType;//资源类型
	
	private boolean checkGrey;//灰度发布


}
