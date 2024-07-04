package org.zkpk.cs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zkpk.cs.common.exception.LabException;
import org.zkpk.cs.common.result.PageBean;
import org.zkpk.cs.common.result.ResultEnum;
import org.zkpk.cs.common.result.ResultMessage;
import org.zkpk.cs.common.utils.CopyUtil;
import org.zkpk.cs.common.utils.IpUtil;
import org.zkpk.cs.common.utils.StringUtil;
import org.zkpk.cs.dto.SysUserDto;
import org.zkpk.cs.dto.SysUserRoleDto;
import org.zkpk.cs.entity.SysUser;
import org.zkpk.cs.entity.SysUserOrganization;
import org.zkpk.cs.entity.SysUserRole;
import org.zkpk.cs.mapper.SysRoleMapper;
import org.zkpk.cs.mapper.SysUserMapper;
import org.zkpk.cs.mapper.SysUserOrganizationMapper;
import org.zkpk.cs.mapper.SysUserRoleMapper;
import org.zkpk.cs.service.SysUserService;
import org.zkpk.cs.shiro.utils.HashPassword;
import org.zkpk.cs.shiro.utils.PasswordHash;

import com.github.pagehelper.PageHelper;


@Service
public class SysUserServiceImpl implements SysUserService{
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private PasswordHash passwordHash;
	
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	
	
	@Autowired
	private SysUserOrganizationMapper sysUserOrganizationMapper;

	

	@Override
	public PageBean<SysUserDto> selectPageBeanUserManageSysUserDtoList(HttpServletRequest request, String string) {
		String pnum = request.getParameter("pageNum");
    	String psize = request.getParameter("pageSize");
		int pageNum = Integer.valueOf(pnum == null ? "1" : pnum);// 当前页
		int pageSize = Integer.valueOf(psize == null ? "10" : psize);// 每页数量	
		String userStatus = request.getParameter("userStatus");// 用户状态
		String loginName = request.getParameter("loginName");// 用户名
		String realName = request.getParameter("realName");// 姓名
		Map<String, Object> parameterMap = new HashMap<String, Object>(); //参数map
		parameterMap.put("userStatus", userStatus);
		parameterMap.put("loginName", loginName);
		parameterMap.put("realName", realName);
		PageHelper.startPage(pageNum, pageSize);
		List<SysUserDto> userlist = sysUserMapper.selectUserDtoListByParameter(parameterMap); //查询集合
		return new PageBean<SysUserDto>(userlist);
	}

	@Override
	public ResultMessage saveOrUpdateSysUser(SysUserDto sysUserDto, String string) throws Exception {
		Date dt = new Date();//系统当前时间
		SysUser sysUser = new SysUser();
		CopyUtil.copyBeans(sysUserDto, sysUser);
		String loginName = sysUserDto.getLoginName();//账号
		String schoolId = sysUserDto.getSchoolId(); // 学校ID
		String teamId = sysUserDto.getTeamId(); // 队伍ID
		String[] choiceRoleList = sysUserDto.getRoles();//用户角色
		if (sysUser.getSuId() != null && !"".equals(sysUser.getSuId())) {
			SysUser result = sysUserMapper.selectByPrimaryKey(sysUser.getSuId());
			if (!result.getLoginName().equals(loginName)) {
				SysUserDto resultuserdto = sysUserMapper.selectUserDtoByLoginName(loginName);
				if (resultuserdto != null) {
					throw new LabException(ResultEnum.CANNOT_ADD_LOGINNAME_EXIST);//账号存在
				}
			}
			sysUser.setUpdateBy("");//变更者
			sysUser.setUpdateTime(dt);//变更时间
			int resultUserFlag = sysUserMapper.updateSysUserByPrimaryKeySelective(sysUser);//修改用户
			//用户组织
			SysUserOrganization resultSysUserOrganization = sysUserOrganizationMapper.selectSysUserOrganizationByUserId(sysUser.getSuId());
			if (resultSysUserOrganization != null) {//单位发生变化
				if (!resultSysUserOrganization.getSchoolId().equals(schoolId) || !resultSysUserOrganization.getTeamId().equals(teamId)) {
					resultSysUserOrganization.setSchoolId(schoolId);//修改单位ID
					resultSysUserOrganization.setTeamId(teamId);//修改队伍ID
					resultSysUserOrganization.setUpdateBy("");//变更者
					resultSysUserOrganization.setUpdateTime(dt);//变更时间
					//修改标记
					sysUserOrganizationMapper.updateByPrimaryKeySelective(resultSysUserOrganization);
				}
			} else {
				SysUserOrganization sysUserOrganization = new SysUserOrganization();
				String suoId = StringUtil.getDateUUId();
				sysUserOrganization.setSuoId(suoId);//主键
				sysUserOrganization.setUserId(sysUser.getSuId());//用户ID
				sysUserOrganization.setSchoolId(schoolId);//学校ID
				sysUserOrganization.setTeamId(teamId);//队伍ID
				sysUserOrganization.setCreateBy("");//作成者
				sysUserOrganization.setCreateTime(dt);//作成时间
				//添加用户组织
				sysUserOrganizationMapper.insertSelective(sysUserOrganization);
			}
			//数据库中已经存在用户角色集合
			Map<String, Object> hasResultMap = new HashMap<String, Object>();
			//用户角色
			List<SysUserRoleDto> userrolelist = sysUserRoleMapper.selectUserRoleListByUserId(sysUser.getSuId());
			if(userrolelist != null && userrolelist.size() > 0){
				for(SysUserRoleDto sysUserRoleDto : userrolelist){
					//角色ID-用户角色ID
					hasResultMap.put(sysUserRoleDto.getRole().getSrId(), sysUserRoleDto.getSurId());
				}
			}
			//待删除数据集合
			List<String> delList = new ArrayList<String>();
			//存储入库集合
			List<String> resultList = new ArrayList<String>();
			//未选角色集合
			List<String> unCheckList = new ArrayList<String>();
			//查询所有角色
			List<String> roleIdList = sysRoleMapper.selectRoleIdList();
			for (String roleId : choiceRoleList) {  
				if (roleIdList.contains(roleId)) {
					roleIdList.remove(roleId);
				}
			}
			for (String roleId : choiceRoleList) {  
				if (hasResultMap.get(roleId) == null) {
					String availObj = String.valueOf(roleId);
					resultList.add(availObj);
				}
			}
			unCheckList = roleIdList;
			for (String roleId : unCheckList) {  
				if (hasResultMap.get(roleId) != null) {
					String hasObj = String.valueOf(hasResultMap.get(roleId));
					delList.add(hasObj);
				}
			}
			//用于封装SysUserRole对象
			List<SysUserRole> userroleList = new ArrayList<SysUserRole>();
			//添加新角色
			for (String roleId: resultList) {
				SysUserRole sysUserRole = new SysUserRole();
				String surId = StringUtil.getDateUUId();
				sysUserRole.setSurId(surId);//主键
				sysUserRole.setUserId(sysUser.getSuId());//用户ID
				sysUserRole.setRoleId(roleId);//角色ID
				sysUserRole.setCreateBy("");//作成者
				sysUserRole.setCreateTime(dt);//作成时间
				userroleList.add(sysUserRole);
			}
			//批量插入用户角色
			int resultBatchFlag = 0;
			if (userroleList != null && userroleList.size() > 0) {
				resultBatchFlag = sysUserRoleMapper.batchInsertSysUserRole(userroleList);
			}
			
			//逻辑删除已有角色
			int resultBatchDeleteFlag = 0;
			if (delList != null && delList.size() > 0) {
				resultBatchDeleteFlag = sysUserRoleMapper.batchLogicallyDeleteSysUserRole(delList);
			}
			
			//是否需要添加和删除
			if (resultList.size() > 0) {
				if (resultUserFlag == 1 && resultBatchFlag == resultList.size()) {//修改成功
					return new ResultMessage().success(ResultEnum.EDIT_SUCCESS);
				} else {
					throw new LabException(ResultEnum.EDIT_FAILED);
				}
			} else if (delList.size() > 0) {
				if (resultUserFlag == 1 && resultBatchDeleteFlag == delList.size()) {//修改成功
					return new ResultMessage().success(ResultEnum.EDIT_SUCCESS);
				} else {
					throw new LabException(ResultEnum.EDIT_FAILED);
				}
			} else if (resultList.size() > 0 && delList.size() > 0) {
				if (resultUserFlag == 1 && resultBatchFlag  == resultList.size() && resultBatchDeleteFlag == delList.size()) {//修改成功
					return new ResultMessage().success(ResultEnum.EDIT_SUCCESS);
				} else {
					throw new LabException(ResultEnum.EDIT_FAILED);
				}
			} else {
				if (resultUserFlag == 1 ) {//修改成功
					return new ResultMessage().success(ResultEnum.EDIT_SUCCESS);
				} else {
					throw new LabException(ResultEnum.EDIT_FAILED);
				}
			}
		} else {
			SysUserDto resultuserdto = sysUserMapper.selectUserDtoByLoginName(loginName);
			if (resultuserdto != null) {
				throw new LabException(ResultEnum.CANNOT_ADD_LOGINNAME_EXIST);//账号存在
			}
			String suId = StringUtil.getDateUUId();
			sysUser.setSuId(suId);//主键
			String initialPassword = StringUtil.getRandNum(8);
			sysUser.setInitialPassword(initialPassword);
			HashPassword hashPassword = passwordHash.encryptPassword(initialPassword);
			sysUser.setPassword(hashPassword.getPassword()); // 密码
			sysUser.setSalt(hashPassword.getSalt()); // 盐度
			sysUser.setUserStatus("USER_STATUS_NORMAL"); // 状态 正常
			sysUser.setCreateBy("");//作成者
			sysUser.setCreateTime(dt);//作成时间
			//添加用户
			int resultUserFlag = sysUserMapper.insertSelective(sysUser);
			//用户组织
			if (schoolId != null && !"".equals(schoolId)) {
				SysUserOrganization sysUserOrganization = new SysUserOrganization();
				String suoId = StringUtil.getDateUUId();
				sysUserOrganization.setSuoId(suoId);//主键
				sysUserOrganization.setUserId(suId);//用户ID
				sysUserOrganization.setSchoolId(schoolId);//学校ID
				sysUserOrganization.setTeamId(teamId);//队伍ID
				sysUserOrganization.setCreateBy("");//作成者
				sysUserOrganization.setCreateTime(dt);//作成时间
				//添加用户组织
				sysUserOrganizationMapper.insertSelective(sysUserOrganization);
			}
			//用户角色ID
			if (choiceRoleList.length > 0) {
				//用于封装SysUserRole对象
				List<SysUserRole> userroleList = new ArrayList<SysUserRole>();
				for (String roleId : choiceRoleList) {  
					SysUserRole sysUserRole = new SysUserRole();
					String surId = StringUtil.getDateUUId();
					sysUserRole.setSurId(surId);//主键
					sysUserRole.setUserId(suId);//用户ID
					sysUserRole.setRoleId(roleId);//角色ID
					sysUserRole.setCreateBy("");//作成者
					sysUserRole.setCreateTime(dt);//作成时间
					userroleList.add(sysUserRole);
				}  
				//批量插入用户角色
				int resultBatchFlag = 0;
				if (userroleList != null && userroleList.size() > 0) {
					resultBatchFlag = sysUserRoleMapper.batchInsertSysUserRole(userroleList);
				}
				if (resultUserFlag == 1 && resultBatchFlag == choiceRoleList.length) {//添加成功
					return new ResultMessage().success(ResultEnum.ADD_SUCCESS);
				} else {
					throw new LabException(ResultEnum.ADD_FAILED);
				}
			} else {
				if (resultUserFlag == 1) {//添加成功
					return new ResultMessage().success(ResultEnum.ADD_SUCCESS);
				} else {
					throw new LabException(ResultEnum.ADD_FAILED);
				}
			}
		}
	}

	@Override
	public List<SysUserRoleDto> selectUserRoleListByUserId(String userId) {
		List<SysUserRoleDto> userrolelist = sysUserRoleMapper.selectUserRoleListByUserId(userId);
		return userrolelist;
	}

	@Override
	public SysUserDto selectUserByLoginName(String loginName) {
		SysUserDto userDto = sysUserMapper.selectUserDtoByLoginName(loginName);
		return userDto;
	}

	@Override
	public SysUserDto selectSysUserByUserId(String suId) {
		//查询用户信息
				SysUserDto sysUserDto = sysUserMapper.selectSysUserDtoByUserId(suId);
				return sysUserDto;
	}

	@Override
	public Map<String, Object> selectUserRoleListMapByUserId(String suId) {
		//结果Map
				Map<String, Object> userRoleMap = new HashMap<String, Object>();
				//用户角色
				List<SysUserRoleDto> userrolelist = sysUserRoleMapper.selectUserRoleListByUserId(suId);
				if(userrolelist != null && userrolelist.size() > 0){
					for(SysUserRoleDto sysUserRoleDto : userrolelist){
						//角色ID-用户角色ID
						userRoleMap.put(sysUserRoleDto.getRole().getSrId(), sysUserRoleDto.getSurId());
					}
				}
				return userRoleMap;
	}

	@Override
	public ResultMessage deleteSysUserByUserId(String suId) {
		SysUser sysUser = sysUserMapper.selectByPrimaryKey(suId);//获取当前用户信息
		if (sysUser != null) {
			Date dt = new Date();
			sysUser.setUpdateBy("");//作成者
			sysUser.setUpdateTime(dt);//作成时间
			sysUser.setIsDeleted(true);//设置为删除
			int resultUserFlag = sysUserMapper.updateByPrimaryKeySelective(sysUser);//修改当前用户
			if (resultUserFlag == 1) {//删除成功
				return new ResultMessage().success(ResultEnum.DELETE_SUCCESS);
			} else {
				throw new LabException(ResultEnum.DELETE_FAILED);
			}
		} else {
			throw new LabException(ResultEnum.NO_OPT_OBJECT);
		}
	}

	@Override
	public ResultMessage batchDeleteSysUserByUserId(String suIds, String string) {
		String[] suIdList = suIds.split(",");
		if (suIdList.length > 0) {
			int resultUserFlag = 0;
			for (String suId : suIdList) {
				SysUser sysUser = sysUserMapper.selectByPrimaryKey(suId);//获取当前用户信息
				Date dt = new Date();
				sysUser.setUpdateBy("");//作成者
				sysUser.setUpdateTime(dt);//作成时间
				sysUser.setIsDeleted(true);//设置为删除
				sysUserMapper.updateByPrimaryKeySelective(sysUser);//修改当前用户
				resultUserFlag ++; 
			}
			if (resultUserFlag == suIdList.length) {
				return new ResultMessage().success(ResultEnum.DELETE_SUCCESS);
			} else {
				throw new LabException(ResultEnum.DELETE_FAILED);
			}
		} else {
			throw new LabException(ResultEnum.NO_OPT_OBJECT);
		}
	}

	@Override
	public ResultMessage updateSysUserPassWd(String suId) {
		SysUser sysUser = sysUserMapper.selectByPrimaryKey(suId);//获取当前用户信息
		if (sysUser != null) {
			Date dt = new Date();
			if (sysUser.getInitialPassword() != null && !"".equals(sysUser.getInitialPassword())) {
				HashPassword hashPassword = passwordHash.encryptPassword(sysUser.getInitialPassword());
				sysUser.setPassword(hashPassword.getPassword()); // 密码
				sysUser.setSalt(hashPassword.getSalt()); // 盐度
			} else {
				String initialPassword = StringUtil.getRandNum(8);
				sysUser.setInitialPassword(initialPassword);
				HashPassword hashPassword = passwordHash.encryptPassword(initialPassword);
				sysUser.setPassword(hashPassword.getPassword()); // 密码
				sysUser.setSalt(hashPassword.getSalt()); // 盐度
			}
			sysUser.setLastChgPwdTime(null);
			sysUser.setUpdateBy("");//作成者
			sysUser.setUpdateTime(dt);//作成时间
			int resultUserFlag = sysUserMapper.updateByPrimaryKey(sysUser);//修改当前用户
			if (resultUserFlag == 1) {//重置成功
				return new ResultMessage().success(ResultEnum.RESETPWD_SUCCESS);
			} else {
				throw new LabException(ResultEnum.RESETPWD_FAILED);
			}
		} else {
			throw new LabException(ResultEnum.NO_OPT_OBJECT);
		}
	}

	@Override
	public ResultMessage updateSysUserStatusDisable(String suId, String string) {
		SysUser sysUser = sysUserMapper.selectByPrimaryKey(suId);//获取当前用户信息
		if (sysUser != null && "USER_STATUS_NORMAL".equals(sysUser.getUserStatus())) {
			Date dt = new Date();
			sysUser.setUserStatus("USER_STATUS_DISABLED"); // 状态 禁用
			sysUser.setUpdateBy("");//作成者
			sysUser.setUpdateTime(dt);//作成时间
			int resultUserFlag = sysUserMapper.updateByPrimaryKeySelective(sysUser);//修改当前用户
			if (resultUserFlag == 1) {//禁用成功
				return new ResultMessage().success(ResultEnum.DISABLED_SUCCESS);
			} else {
				throw new LabException(ResultEnum.DISABLED_FAILED);
			}
		} else {
			throw new LabException(ResultEnum.NO_OPT_OBJECT);
		}
	}

	@Override
	public ResultMessage updateSysUserStatusEnable(String suId, String string) {
		SysUser sysUser = sysUserMapper.selectByPrimaryKey(suId);//获取当前用户信息
		if (sysUser != null) {
			Date dt = new Date();
			sysUser.setUserStatus("USER_STATUS_NORMAL"); // 状态 正常
			sysUser.setUpdateBy("");//作成者
			sysUser.setUpdateTime(dt);//作成时间
			int resultUserFlag = sysUserMapper.updateByPrimaryKeySelective(sysUser);//修改当前用户
			if (resultUserFlag == 1) {//禁用成功
				return new ResultMessage().success(ResultEnum.ENABLE_SUCCESS);
			} else {
				throw new LabException(ResultEnum.ENABLE_FAILED);
			}
		} else {
			throw new LabException(ResultEnum.NO_OPT_OBJECT);
		}
	}

	@Override
	public ResultMessage updateSysUserStatusUnLock(String suId, String string) {
		SysUser sysUser = sysUserMapper.selectByPrimaryKey(suId);//获取当前用户信息
		if (sysUser != null) {
			Date dt = new Date();
			sysUser.setUserStatus("USER_STATUS_NORMAL"); // 状态 正常
			sysUser.setUpdateBy("");//作成者
			sysUser.setUpdateTime(dt);//作成时间
			int resultUserFlag = sysUserMapper.updateByPrimaryKeySelective(sysUser);//修改当前用户
			if (resultUserFlag == 1) {//禁用成功
				return new ResultMessage().success(ResultEnum.UNLOCK_SUCCESS);
			} else {
				throw new LabException(ResultEnum.UNLOCK_FAILED);
			}
		} else {
			throw new LabException(ResultEnum.NO_OPT_OBJECT);
		}
	}

	@Override
	public void updateUserLoginTimeByUserId(HttpServletRequest request, String userId) {
		//更新个人信息
				SysUser user = sysUserMapper.selectByPrimaryKey(userId);
				String loginIp = IpUtil.getIpFromRequest(WebUtils.toHttp(request));//获取登陆IP
				Date dt = new Date();
				user.setLastLoginIp(loginIp);//最后登录IP
				user.setLoginTime(dt); //登录时间
				user.setUpdateTime(dt); //更新时间
				user.setUpdateBy(userId);// 更新人
				sysUserMapper.updateByPrimaryKeySelective(user); //更新登录时间
		
	}

	@Override
	public void updateUserLoginInfoByUserId(HttpServletRequest request, String userId) {
		//更新个人信息
				SysUser user = sysUserMapper.selectByPrimaryKey(userId);
				String loginIp = IpUtil.getIpFromRequest(WebUtils.toHttp(request));//获取登陆IP
				Date dt = new Date();
				user.setLastLoginTime(dt); //上次登录时间
				user.setLastLoginIp(loginIp);//最后登录IP
				user.setUpdateTime(dt); //更新时间
				user.setUpdateBy(userId);// 更新人
				sysUserMapper.updateByPrimaryKeySelective(user); //更新登录时间
		
	}

	

}
