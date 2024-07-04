package org.zkpk.cs.dto;

import java.io.Serializable;
import java.util.Date;

import org.zkpk.cs.entity.SysRole;
import org.zkpk.cs.entity.SysUser;


public class SysUserRoleDto implements Serializable {

   private static final long serialVersionUID = 1032050051366621381L;

   private String surId;

   private SysUser user;

   private SysRole role;

   private String createBy;

   private Date createTime;

   private String updateBy;

   private Date updateTime;

   private Boolean isDeleted;

   public String getSurId() {
       return surId;
   }

   public void setSurId(String surId) {
       this.surId = surId == null ? null : surId.trim();
   }

   public SysUser getUser() {
	   return user;
   }

   public void setUser(SysUser user) {
	   this.user = user;
   }

   public SysRole getRole() {
	   return role;
   }

   public void setRole(SysRole role) {
	   this.role = role;
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

}
