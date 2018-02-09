package com.jianq.manager.spm.user.entity;

/**
 * 财迷用户
 */
public class User {
	/**
	 * 用户编号
	 */
	private Integer userId;
	/**
	 * 用户编码
	 */
	private String userCode;
	/**
	 * 姓名
	 */
	private String userName;
	/**
	 * 所属机构编码
	 */
	private String orgId;
	/**
	 * 所属机构名称
	 */
	private String orgName;

	/**
	 * 姓名
	 */
	private String realName;

	public User() {
		super();
	}

	public User(Integer userId, String userCode) {
		super();
		this.userId = userId;
		this.userCode = userCode;
	}

	public User(String orgId) {
		super();
		this.orgId = orgId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode == null ? null : userCode.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId == null ? null : orgId.trim();
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName == null ? null : orgName.trim();
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", userCode='" + userCode + '\'' +
				", userName='" + userName + '\'' +
				", orgId='" + orgId + '\'' +
				", orgName='" + orgName + '\'' +
				", realName='" + realName + '\'' +
				'}';
	}
}