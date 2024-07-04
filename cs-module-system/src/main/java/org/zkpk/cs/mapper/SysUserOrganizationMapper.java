package org.zkpk.cs.mapper;

import org.zkpk.cs.entity.SysUserOrganization;

public interface SysUserOrganizationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_organization
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String suoId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_organization
     *
     * @mbg.generated
     */
    int insert(SysUserOrganization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_organization
     *
     * @mbg.generated
     */
    int insertSelective(SysUserOrganization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_organization
     *
     * @mbg.generated
     */
    SysUserOrganization selectByPrimaryKey(String suoId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_organization
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SysUserOrganization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_organization
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysUserOrganization record);

	SysUserOrganization selectSysUserOrganizationByUserId(String suId);
}