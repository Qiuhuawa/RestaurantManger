package org.zkpk.cs.mapper;

import java.util.List;

import org.zkpk.cs.dto.SysUserRoleDto;
import org.zkpk.cs.entity.SysUserRole;

public interface SysUserRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String surId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated
     */
    int insert(SysUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated
     */
    int insertSelective(SysUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated
     */
    SysUserRole selectByPrimaryKey(String surId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SysUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysUserRole record);

	List<SysUserRoleDto> selectUserRoleListByRoleId(String srId);

	int batchInsertSysUserRole(List<SysUserRole> userroleList);

	List<SysUserRoleDto> selectUserRoleListByUserId(String suId);

	int batchLogicallyDeleteSysUserRole(List<String> delList);
}