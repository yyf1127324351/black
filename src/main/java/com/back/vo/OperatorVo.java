package com.back.vo;

import java.io.Serializable;
import java.util.List;

/**
 * HR人员信息
 */
public class OperatorVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2747483499806239024L;
	private long operatorId;//人员ID
	private String operatorName;//中文名
	private String operatorLoginName;//域帐户
	private String operatorNo;//工号
	private String operatorEmail;//邮箱
	private long departmentId;//所属最末级部门ID
	private String departmentPath;//部门全路径id
	private String mobile;//手机号
	private String operatorOnDeptId;//所属最末级部门code
	private int officeArea;//工作区域id
	private String officeAreaName;//工作区域id
	private String code;//人员code，ERP人员PKID
	private int operatorIsAdmin;//是否管理员
	private int operatorSex;//员工性别(1：男2：女 )
	private String headImg;//头像url
	
	private String departmentName;//所属最末级部门部门名称
	private String departmentPathName;//部门全路径名称
	private List<String> departmentPaths;//所属部门id集合
	private List<String> departmentPathNames;//所属部门名称集合
	private String loginTime;//登录时间
	
	private String ip;//当前登录IP
	private String jobName;//岗位名称
	
	private String loginSource;//登录来源sso和erp
	private boolean checkGrey;//是否为白名单，true为白名单
	
	private String orgId;//人员所在的ERP组织结构ID
	private String orgName;//人员所在的ERP组织结构名称
	
	private String qq;//ERP同步过来的QQ
	private String fax;//ERP同步过来的传真
	private String telPhone;//ERP同步过来的telphone
	
	private String maxExpCount;
	private String idEntityNo;
	
	public long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOperatorLoginName() {
		return operatorLoginName;
	}
	public void setOperatorLoginName(String operatorLoginName) {
		this.operatorLoginName = operatorLoginName;
	}
	public String getOperatorNo() {
		return operatorNo;
	}
	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}
	public String getOperatorEmail() {
		return operatorEmail;
	}
	public void setOperatorEmail(String operatorEmail) {
		this.operatorEmail = operatorEmail;
	}
	public long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentPath() {
		return departmentPath;
	}
	public void setDepartmentPath(String departmentPath) {
		this.departmentPath = departmentPath;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOperatorOnDeptId() {
		return operatorOnDeptId;
	}
	public void setOperatorOnDeptId(String operatorOnDeptId) {
		this.operatorOnDeptId = operatorOnDeptId;
	}
	public int getOfficeArea() {
		return officeArea;
	}
	public void setOfficeArea(int officeArea) {
		this.officeArea = officeArea;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getOperatorIsAdmin() {
		return operatorIsAdmin;
	}
	public void setOperatorIsAdmin(int operatorIsAdmin) {
		this.operatorIsAdmin = operatorIsAdmin;
	}
	public int getOperatorSex() {
		return operatorSex;
	}
	public void setOperatorSex(int operatorSex) {
		this.operatorSex = operatorSex;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDepartmentPathName() {
		return departmentPathName;
	}
	public void setDepartmentPathName(String departmentPathName) {
		this.departmentPathName = departmentPathName;
	}
	public List<String> getDepartmentPaths() {
		return departmentPaths;
	}
	public void setDepartmentPaths(List<String> departmentPaths) {
		this.departmentPaths = departmentPaths;
	}
	public List<String> getDepartmentPathNames() {
		return departmentPathNames;
	}
	public void setDepartmentPathNames(List<String> departmentPathNames) {
		this.departmentPathNames = departmentPathNames;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getLoginSource() {
		return loginSource;
	}
	public void setLoginSource(String loginSource) {
		this.loginSource = loginSource;
	}
	public boolean isCheckGrey() {
		return checkGrey;
	}
	public void setCheckGrey(boolean checkGrey) {
		this.checkGrey = checkGrey;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getMaxExpCount() {
		return maxExpCount;
	}
	public void setMaxExpCount(String maxExpCount) {
		this.maxExpCount = maxExpCount;
	}
	public String getIdEntityNo() {
		return idEntityNo;
	}
	public void setIdEntityNo(String idEntityNo) {
		this.idEntityNo = idEntityNo;
	}
	public String getOfficeAreaName() {
		return officeAreaName;
	}
	public void setOfficeAreaName(String officeAreaName) {
		this.officeAreaName = officeAreaName;
	}
}
