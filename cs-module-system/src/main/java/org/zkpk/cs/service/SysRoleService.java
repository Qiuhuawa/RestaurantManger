package org.zkpk.cs.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkpk.cs.common.result.PageBean;
import org.zkpk.cs.common.result.ResultMessage;
import org.zkpk.cs.common.result.Tree;
import org.zkpk.cs.dto.SysRoleDto;
import org.zkpk.cs.dto.SysRoleResourceDto;
import org.zkpk.cs.entity.SysRole;

public interface SysRoleService {

	PageBean<SysRole> selectPageBeanRoleList(HttpServletRequest request, String string);

	List<Tree> selectResourceListTreeByRoleId(String roleId);

	ResultMessage saveOrUpdateSysRole(HttpServletRequest request, SysRoleDto sysRoleDto, String string) throws Exception;

	SysRoleDto selectSysRoleDtoByRoleId(String srId) throws Exception;

	ResultMessage deleteSysRoleByRoleId(String srId);

	Map<String, SysRole> selectShowRoleMap();

	List<SysRole> selectRoleList();

	List<SysRoleResourceDto> selectRoleResourceListByRoleId(String srId);

}
