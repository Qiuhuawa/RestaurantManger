package org.zkpk.cs.dto;

import java.io.Serializable;
import java.util.Date;

import org.zkpk.cs.entity.SysUserOrganization;

public class SysUserDto implements Serializable {
	
   private static final long serialVersionUID = -6152249928367999085L;

   private String suId;

   private String loginName;
   
   private String initialPassword;
   
   private String password;

   private String salt;

   private String userNumber;

   private String nickname;

   private String realName;

   private String sex;

   private Date birthday;

   private String mobile;

   private String email;

   private String signature;

   private Date loginTime;

   private Date lastLoginTime;

   private String lastLoginIp;

   private Date lastChgPwdTime;

   private Integer loginErrorCount;

   private String userStatus;

   private String avatar;

   private Date closingDate;

   private String createBy;

   private Date createTime;

   private String updateBy;

   private Date updateTime;

   private Boolean isDeleted;
   
   private SysUserOrganization sysUserOrganization;
   
   private String schoolName;
   
   private String teamName;
   
   private String schoolId;
   
   private String teamId;
   
   private String[] roles;
   
   private String orgId;
   
   public static long getSerialversionuid() {
	   return serialVersionUID;
   }

   public String getSuId() {
       return suId;
   }

   public void setSuId(String suId) {
       this.suId = suId == null ? null : suId.trim();
   }

   public String getLoginName() {
       return loginName;
   }

   public void setLoginName(String loginName) {
       this.loginName = loginName == null ? null : loginName.trim();
   }

   public String getInitialPassword() {
	   return initialPassword;
   }
	
   public void setInitialPassword(String initialPassword) {
	   this.initialPassword = initialPassword;
   }
	
   public String getPassword() {
	   return password;
   }

   public void setPassword(String password) {
	   this.password = password;
   }

   public String getSalt() {
       return salt;
   }

   public void setSalt(String salt) {
       this.salt = salt == null ? null : salt.trim();
   }

   public String getUserNumber() {
       return userNumber;
   }

   public void setUserNumber(String userNumber) {
       this.userNumber = userNumber == null ? null : userNumber.trim();
   }

   public String getNickname() {
       return nickname;
   }

   public void setNickname(String nickname) {
       this.nickname = nickname == null ? null : nickname.trim();
   }

   public String getRealName() {
       return realName;
   }

   public void setRealName(String realName) {
       this.realName = realName == null ? null : realName.trim();
   }

   public String getSex() {
       return sex;
   }

   public void setSex(String sex) {
       this.sex = sex == null ? null : sex.trim();
   }

   public Date getBirthday() {
       return birthday;
   }

   public void setBirthday(Date birthday) {
       this.birthday = birthday;
   }

   public String getMobile() {
       return mobile;
   }

   public void setMobile(String mobile) {
       this.mobile = mobile == null ? null : mobile.trim();
   }

   public String getEmail() {
       return email;
   }

   public void setEmail(String email) {
       this.email = email == null ? null : email.trim();
   }

   public String getSignature() {
       return signature;
   }

   public void setSignature(String signature) {
       this.signature = signature == null ? null : signature.trim();
   }

   public Date getLoginTime() {
       return loginTime;
   }

   public void setLoginTime(Date loginTime) {
       this.loginTime = loginTime;
   }

   public Date getLastLoginTime() {
       return lastLoginTime;
   }

   public void setLastLoginTime(Date lastLoginTime) {
       this.lastLoginTime = lastLoginTime;
   }

   public String getLastLoginIp() {
       return lastLoginIp;
   }

   public void setLastLoginIp(String lastLoginIp) {
       this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
   }

   public Date getLastChgPwdTime() {
       return lastChgPwdTime;
   }

   public void setLastChgPwdTime(Date lastChgPwdTime) {
       this.lastChgPwdTime = lastChgPwdTime;
   }

   public Integer getLoginErrorCount() {
       return loginErrorCount;
   }

   public void setLoginErrorCount(Integer loginErrorCount) {
       this.loginErrorCount = loginErrorCount;
   }

   public String getUserStatus() {
       return userStatus;
   }

   public void setUserStatus(String userStatus) {
       this.userStatus = userStatus == null ? null : userStatus.trim();
   }

   public String getAvatar() {
       return avatar;
   }

   public void setAvatar(String avatar) {
       this.avatar = avatar == null ? null : avatar.trim();
   }

   public Date getClosingDate() {
       return closingDate;
   }

   public void setClosingDate(Date closingDate) {
       this.closingDate = closingDate;
   }

   public SysUserOrganization getSysUserOrganization() {
	   return sysUserOrganization;
   }

   public void setSysUserOrganization(SysUserOrganization sysUserOrganization) {
	   this.sysUserOrganization = sysUserOrganization;
   }

   public String getSchoolName() {
	   return schoolName;
   }
	
   public void setSchoolName(String schoolName) {
	   this.schoolName = schoolName;
   }
	
   public String getTeamName() {
	   return teamName;
   }

   public void setTeamName(String teamName) {
	   this.teamName = teamName;
   }

   public String getCreateBy() {
       return createBy;
   }

   public void setCreateBy(String createBy) {
       this.createBy = createBy == null ? null : createBy.trim();
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
       this.updateBy = updateBy == null ? null : updateBy.trim();
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
	
   public String[] getRoles() {
	   return roles;
   }
	
   public void setRoles(String[] roles) {
	   this.roles = roles;
   }

   public String getOrgId() {
	   return orgId;
   }
	
   public void setOrgId(String orgId) {
	   this.orgId = orgId;
   }

}
