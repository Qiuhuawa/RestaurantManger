package org.zkpk.cs.controller.auth;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.zkpk.cs.common.result.PageBean;
import org.zkpk.cs.common.result.ResultEnum;
import org.zkpk.cs.common.result.ResultMessage;
import org.zkpk.cs.common.result.Tree;
import org.zkpk.cs.controller.base.BaseController;
import org.zkpk.cs.dto.SysUserDto;
import org.zkpk.cs.entity.SysRole;
import org.zkpk.cs.entity.SysUser;
import org.zkpk.cs.service.SysRoleService;
import org.zkpk.cs.service.SysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value="用户管理", tags = "用户")
@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController{
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    
	private static final String LOGIN_PAGE = "login"; //登录页面
	private static final String LIST_USER_PAGE = "auth/user/list_user"; //
	private static final String EDIT_USER_PAGE = "auth/user/edit_user"; //
    
	/*
	@ApiOperation(value="用户管理", notes="跳转到用户管理", httpMethod="POST")
	@RequestMapping(value = "/listuser", method = RequestMethod.POST)
	public ModelAndView listResource(ModelAndView mav) {
	    mav.setViewName(LIST_USER_PAGE);
	    return mav;
	}
    @ApiOperation(value="添加编辑用户页面", notes="跳转到超管用户添加编辑页面", httpMethod="GET")
	@RequestMapping(value = "/getuser", method = RequestMethod.GET)
	public @ResponseBody ResultMessage getUser(String suId) throws Exception {
	
    		SysUser sysUserDto = sysUserService.selectSysUserByUserId("2");//查询用户信息
	    	return new ResultMessage().success(ResultEnum.SUCCESS)
	    			.addData("user", sysUserDto);
	}*/
	
	@ApiOperation(value="用户管理", notes="跳转到用户管理", httpMethod="POST")
	@RequestMapping(value = "/listuser", method = RequestMethod.POST)
	public ModelAndView listUser(ModelAndView mav) {
		Map<String, Object> dictMap = getMapDictData("USER_STATUS");//用户状态Map
		mav.addObject("userStatusSelectMap", dictMap);//用户状态Map
		Map<String, Object> sexMap = getMapDictData("SEX_TYPE");//性别状态Map
		mav.addObject("sexTypeMap", sexMap);//用户状态Map
		Map<String, SysRole> roleMap = sysRoleService.selectShowRoleMap();//角色Map
    	mav.addObject("roleMap", roleMap);
		mav.setViewName(LIST_USER_PAGE);
	    return mav;
	}
	
	@ApiOperation(value="用户管理", notes="用户管理用户信息", httpMethod="POST")
	@RequestMapping(value = "/listuserdata", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listUserData(HttpServletRequest request) {
    	PageBean<SysUserDto> result = sysUserService.selectPageBeanUserManageSysUserDtoList(request, "");
    	return getDataTable(result);
	}
	
	@ApiOperation(value="保存修改用户", notes="保存修改用户方法", httpMethod="POST")
	@RequestMapping(value = "/saveorupdateuser", method = RequestMethod.POST)
    public @ResponseBody ResultMessage saveOrUpdateUser(SysUserDto sysUserDto) throws Exception {
    	return sysUserService.saveOrUpdateSysUser(sysUserDto, "");
	}
	
	@ApiOperation(value="添加编辑用户页面", notes="跳转到超管用户添加编辑页面", httpMethod="POST")
	@RequestMapping(value = "/getuser", method = RequestMethod.POST)
	public @ResponseBody ResultMessage getUser(String suId) throws Exception {
		SysUserDto sysUserDto = sysUserService.selectSysUserByUserId(suId);//查询用户信息
		Map<String, Object> userRoleMap = sysUserService.selectUserRoleListMapByUserId(suId);
    	return new ResultMessage().success(ResultEnum.SUCCESS)
    			.addData("user", sysUserDto)
    			.addData("userRoleMap", userRoleMap);//用户角色信息
	}

	@ApiOperation(value="删除用户", notes="删除用户方法", httpMethod="POST")
	@RequestMapping(value = "/removeuser", method = RequestMethod.POST)
	public @ResponseBody ResultMessage removeUser(HttpServletRequest request, String suId) throws Exception {
		return sysUserService.deleteSysUserByUserId(suId);
	}

	@ApiOperation(value="更新密码", notes="更新用户密码", httpMethod="POST")
	@RequestMapping(value = "/updateuserpass", method = RequestMethod.POST)
	public @ResponseBody ResultMessage updateUserPassword(HttpServletRequest request, String suId) throws Exception {
		return sysUserService.updateSysUserPassWd(suId);
	}
}