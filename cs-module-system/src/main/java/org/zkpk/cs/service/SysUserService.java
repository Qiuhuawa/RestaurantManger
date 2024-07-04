package org.zkpk.cs.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkpk.cs.common.result.PageBean;
import org.zkpk.cs.common.result.ResultMessage;
import org.zkpk.cs.dto.SysUserDto;
import org.zkpk.cs.dto.SysUserRoleDto;
import org.zkpk.cs.entity.SysUser;

public interface SysUserService {
	

	public PageBean<SysUserDto> selectPageBeanUserManageSysUserDtoList(HttpServletRequest request, String string);

	public ResultMessage saveOrUpdateSysUser(SysUserDto sysUserDto, String string) throws Exception;

	public List<SysUserRoleDto> selectUserRoleListByUserId(String id);

	public SysUserDto selectUserByLoginName(String loginName);

	public SysUserDto selectSysUserByUserId(String suId);

	public Map<String, Object> selectUserRoleListMapByUserId(String suId);

	public ResultMessage deleteSysUserByUserId(String suId);

	public ResultMessage batchDeleteSysUserByUserId(String suIds, String string);

	public ResultMessage updateSysUserPassWd(String suId);

	public ResultMessage updateSysUserStatusDisable(String suId, String string);

	public ResultMessage updateSysUserStatusEnable(String suId, String string);

	public ResultMessage updateSysUserStatusUnLock(String suId, String string);

	public void updateUserLoginTimeByUserId(HttpServletRequest request, String id);

	public void updateUserLoginInfoByUserId(HttpServletRequest request, String userId);

	

}
