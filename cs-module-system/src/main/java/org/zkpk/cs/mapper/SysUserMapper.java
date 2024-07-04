package org.zkpk.cs.mapper;

import java.util.List;
import java.util.Map;

import org.zkpk.cs.dto.SysUserDto;
import org.zkpk.cs.entity.SysUser;

public interface SysUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String suId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated
     */
    int insert(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated
     */
    int insertSelective(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated
     */
    SysUser selectByPrimaryKey(String suId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysUser record);

	List<SysUserDto> selectUserDtoListByParameter(Map<String, Object> parameterMap);

	SysUserDto selectUserDtoByLoginName(String loginName);

	int updateSysUserByPrimaryKeySelective(SysUser sysUser);

	SysUserDto selectSysUserDtoByUserId(String suId);

}