package org.zkpk.cs.dto;

import java.util.Date;

public class SysRoleDto {
	
	private String srId;
	
	private String roleType;
	
	private String roleCode;
	
	private String roleName;
	
	private String description;
	
	private Boolean isOriginalRole;
	
	private Boolean isNeedAuth;
	
	private Boolean isNeedOrg;
	
	private Boolean isShow;
	
	private Integer priority;
	
	private String createBy;
	
	private Date createTime;
	
	private String updateBy;
	
	private Date updateTime;
	
	private Boolean isDeleted;
	
	private String resourceIdStr;
	
	private String[] resourceIds;

	public String getSrId() {
		return srId;
	}

	public void setSrId(String srId) {
		this.srId = srId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsOriginalRole() {
		return isOriginalRole;
	}

	public void setIsOriginalRole(Boolean isOriginalRole) {
		this.isOriginalRole = isOriginalRole;
	}

	public Boolean getIsNeedAuth() {
		return isNeedAuth;
	}

	public void setIsNeedAuth(Boolean isNeedAuth) {
		this.isNeedAuth = isNeedAuth;
	}

	public Boolean getIsNeedOrg() {
		return isNeedOrg;
	}

	public void setIsNeedOrg(Boolean isNeedOrg) {
		this.isNeedOrg = isNeedOrg;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
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

	public String getResourceIdStr() {
		return resourceIdStr;
	}

	public void setResourceIdStr(String resourceIdStr) {
		this.resourceIdStr = resourceIdStr;
	}

	public String[] getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String[] resourceIds) {
		this.resourceIds = resourceIds;
	}

}
