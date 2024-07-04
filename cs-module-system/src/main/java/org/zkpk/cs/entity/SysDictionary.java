package org.zkpk.cs.entity;

import java.io.Serializable;
import java.util.Date;

public class SysDictionary implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dictionary.sd_id
     *
     * @mbg.generated
     */
    private String sdId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dictionary.parent_sd_id
     *
     * @mbg.generated
     */
    private String parentSdId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dictionary.target_code
     *
     * @mbg.generated
     */
    private String targetCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dictionary.dict_code
     *
     * @mbg.generated
     */
    private String dictCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dictionary.dict_name
     *
     * @mbg.generated
     */
    private String dictName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dictionary.dict_remark
     *
     * @mbg.generated
     */
    private String dictRemark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dictionary.priority
     *
     * @mbg.generated
     */
    private Integer priority;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dictionary.is_available
     *
     * @mbg.generated
     */
    private Boolean isAvailable;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dictionary.create_by
     *
     * @mbg.generated
     */
    private String createBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dictionary.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dictionary.update_by
     *
     * @mbg.generated
     */
    private String updateBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dictionary.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dictionary.is_deleted
     *
     * @mbg.generated
     */
    private Boolean isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_dictionary
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dictionary.sd_id
     *
     * @return the value of sys_dictionary.sd_id
     *
     * @mbg.generated
     */
    public String getSdId() {
        return sdId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dictionary.sd_id
     *
     * @param sdId the value for sys_dictionary.sd_id
     *
     * @mbg.generated
     */
    public void setSdId(String sdId) {
        this.sdId = sdId == null ? null : sdId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dictionary.parent_sd_id
     *
     * @return the value of sys_dictionary.parent_sd_id
     *
     * @mbg.generated
     */
    public String getParentSdId() {
        return parentSdId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dictionary.parent_sd_id
     *
     * @param parentSdId the value for sys_dictionary.parent_sd_id
     *
     * @mbg.generated
     */
    public void setParentSdId(String parentSdId) {
        this.parentSdId = parentSdId == null ? null : parentSdId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dictionary.target_code
     *
     * @return the value of sys_dictionary.target_code
     *
     * @mbg.generated
     */
    public String getTargetCode() {
        return targetCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dictionary.target_code
     *
     * @param targetCode the value for sys_dictionary.target_code
     *
     * @mbg.generated
     */
    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode == null ? null : targetCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dictionary.dict_code
     *
     * @return the value of sys_dictionary.dict_code
     *
     * @mbg.generated
     */
    public String getDictCode() {
        return dictCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dictionary.dict_code
     *
     * @param dictCode the value for sys_dictionary.dict_code
     *
     * @mbg.generated
     */
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode == null ? null : dictCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dictionary.dict_name
     *
     * @return the value of sys_dictionary.dict_name
     *
     * @mbg.generated
     */
    public String getDictName() {
        return dictName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dictionary.dict_name
     *
     * @param dictName the value for sys_dictionary.dict_name
     *
     * @mbg.generated
     */
    public void setDictName(String dictName) {
        this.dictName = dictName == null ? null : dictName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dictionary.dict_remark
     *
     * @return the value of sys_dictionary.dict_remark
     *
     * @mbg.generated
     */
    public String getDictRemark() {
        return dictRemark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dictionary.dict_remark
     *
     * @param dictRemark the value for sys_dictionary.dict_remark
     *
     * @mbg.generated
     */
    public void setDictRemark(String dictRemark) {
        this.dictRemark = dictRemark == null ? null : dictRemark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dictionary.priority
     *
     * @return the value of sys_dictionary.priority
     *
     * @mbg.generated
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dictionary.priority
     *
     * @param priority the value for sys_dictionary.priority
     *
     * @mbg.generated
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dictionary.is_available
     *
     * @return the value of sys_dictionary.is_available
     *
     * @mbg.generated
     */
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dictionary.is_available
     *
     * @param isAvailable the value for sys_dictionary.is_available
     *
     * @mbg.generated
     */
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dictionary.create_by
     *
     * @return the value of sys_dictionary.create_by
     *
     * @mbg.generated
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dictionary.create_by
     *
     * @param createBy the value for sys_dictionary.create_by
     *
     * @mbg.generated
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dictionary.create_time
     *
     * @return the value of sys_dictionary.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dictionary.create_time
     *
     * @param createTime the value for sys_dictionary.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dictionary.update_by
     *
     * @return the value of sys_dictionary.update_by
     *
     * @mbg.generated
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dictionary.update_by
     *
     * @param updateBy the value for sys_dictionary.update_by
     *
     * @mbg.generated
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dictionary.update_time
     *
     * @return the value of sys_dictionary.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dictionary.update_time
     *
     * @param updateTime the value for sys_dictionary.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dictionary.is_deleted
     *
     * @return the value of sys_dictionary.is_deleted
     *
     * @mbg.generated
     */
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dictionary.is_deleted
     *
     * @param isDeleted the value for sys_dictionary.is_deleted
     *
     * @mbg.generated
     */
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dictionary
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SysDictionary other = (SysDictionary) that;
        return (this.getSdId() == null ? other.getSdId() == null : this.getSdId().equals(other.getSdId()))
            && (this.getParentSdId() == null ? other.getParentSdId() == null : this.getParentSdId().equals(other.getParentSdId()))
            && (this.getTargetCode() == null ? other.getTargetCode() == null : this.getTargetCode().equals(other.getTargetCode()))
            && (this.getDictCode() == null ? other.getDictCode() == null : this.getDictCode().equals(other.getDictCode()))
            && (this.getDictName() == null ? other.getDictName() == null : this.getDictName().equals(other.getDictName()))
            && (this.getDictRemark() == null ? other.getDictRemark() == null : this.getDictRemark().equals(other.getDictRemark()))
            && (this.getPriority() == null ? other.getPriority() == null : this.getPriority().equals(other.getPriority()))
            && (this.getIsAvailable() == null ? other.getIsAvailable() == null : this.getIsAvailable().equals(other.getIsAvailable()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dictionary
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSdId() == null) ? 0 : getSdId().hashCode());
        result = prime * result + ((getParentSdId() == null) ? 0 : getParentSdId().hashCode());
        result = prime * result + ((getTargetCode() == null) ? 0 : getTargetCode().hashCode());
        result = prime * result + ((getDictCode() == null) ? 0 : getDictCode().hashCode());
        result = prime * result + ((getDictName() == null) ? 0 : getDictName().hashCode());
        result = prime * result + ((getDictRemark() == null) ? 0 : getDictRemark().hashCode());
        result = prime * result + ((getPriority() == null) ? 0 : getPriority().hashCode());
        result = prime * result + ((getIsAvailable() == null) ? 0 : getIsAvailable().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dictionary
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sdId=").append(sdId);
        sb.append(", parentSdId=").append(parentSdId);
        sb.append(", targetCode=").append(targetCode);
        sb.append(", dictCode=").append(dictCode);
        sb.append(", dictName=").append(dictName);
        sb.append(", dictRemark=").append(dictRemark);
        sb.append(", priority=").append(priority);
        sb.append(", isAvailable=").append(isAvailable);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}