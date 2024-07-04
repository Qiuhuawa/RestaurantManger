package org.zkpk.cs.dto;

import java.util.Date;

public class SysLogDto {
	
	private String slId;

    private String userId;

    private String loginName;

    private String realName;
    
    private String schoolName;
    
    private String gropuName;

    private String operationIp;

    private String operationLocation;

    private Long operationTime;
    
    private String operation;

    private String method;

    private String params;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private Boolean isDeleted;

	public String getSlId() {
		return slId;
	}

	public void setSlId(String slId) {
		this.slId = slId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getGropuName() {
		return gropuName;
	}

	public void setGropuName(String gropuName) {
		this.gropuName = gropuName;
	}

	public String getOperationIp() {
		return operationIp;
	}

	public void setOperationIp(String operationIp) {
		this.operationIp = operationIp;
	}

	public String getOperationLocation() {
		return operationLocation;
	}

	public void setOperationLocation(String operationLocation) {
		this.operationLocation = operationLocation;
	}

	public Long getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Long operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
