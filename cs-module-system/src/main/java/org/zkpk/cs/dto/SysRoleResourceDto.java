package org.zkpk.cs.dto;

import java.io.Serializable;
import java.util.Date;

import org.zkpk.cs.entity.SysResource;
import org.zkpk.cs.entity.SysRole;

public class SysRoleResourceDto implements Serializable {
   
   private static final long serialVersionUID = -7265896640627322032L;

   private String srrId;

   private SysRole role;

   private SysResource resource;
   
   private String resourceScope;

   private String createBy;

   private Date createTime;

   private String updateBy;

   private Date updateTime;

   private Boolean isDeleted;

   public String getSrrId() {
       return srrId;
   }

   public void setSrrId(String srrId) {
       this.srrId = srrId == null ? null : srrId.trim();
   }

   public SysRole getRole() {
	   return role;
   }

   public void setRole(SysRole role) {
	   this.role = role;
   }

   public SysResource getResource() {
	   return resource;
   }

   public void setResource(SysResource resource) {
	   this.resource = resource;
   }

   public String getResourceScope() {
	   return resourceScope;
   }

   public void setResourceScope(String resourceScope) {
	   this.resourceScope = resourceScope;
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
