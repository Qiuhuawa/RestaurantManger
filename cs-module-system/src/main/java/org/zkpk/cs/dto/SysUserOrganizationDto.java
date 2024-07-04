package org.zkpk.cs.dto;

import java.io.Serializable;
import java.util.Date;

import org.zkpk.cs.entity.SysUser;

public class SysUserOrganizationDto implements Serializable {

	private static final long serialVersionUID = -1389163659787729404L;
	
	private String suoId;
	
	private SysUser user;
	
	private String schoolId;
	
	private String teamId;
	
	private String createBy;
	
	private Date createTime;
	
	private String updateBy;
	
	private Date updateTime;
	
	private Boolean isDeleted;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSuoId() {
		return suoId;
	}

	public void setSuoId(String suoId) {
		this.suoId = suoId;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
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
