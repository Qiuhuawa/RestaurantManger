package org.zkpk.cs.controller.auth;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.zkpk.cs.common.result.PageBean;
import org.zkpk.cs.common.result.ResultEnum;
import org.zkpk.cs.common.result.ResultMessage;
import org.zkpk.cs.common.result.Tree;
import org.zkpk.cs.controller.base.BaseController;
import org.zkpk.cs.dto.SysRoleDto;
import org.zkpk.cs.entity.SysRole;
import org.zkpk.cs.service.SysRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="角色管理", tags = "角色")
@Controller
@RequestMapping(value="/role")
public class RoleContoller extends BaseController{

	
	@Autowired
	private SysRoleService sysRoleService;
	
	//日志
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    
	private static final String LOGIN_PAGE = "login"; //登录页面
	private static final String LIST_ROLE_PAGE = "auth/role/list_role"; //
	private static final String EDIT_ROLE_PAGE = "auth/role/edit_role"; //
	
	@ApiOperation(value="角色管理", notes="跳转到角色管理", httpMethod="POST")
	@RequestMapping(value = "/listrole", method = RequestMethod.POST)
	public ModelAndView listResource(ModelAndView mav) {
	    mav.setViewName(LIST_ROLE_PAGE);
	    return mav;
	}
	
	@ApiOperation(value="角色信息管理", notes="角色管理用户信息", httpMethod="POST")
	@RequestMapping(value = "/listroledata", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listRoleData(HttpServletRequest request) {
    	PageBean<SysRole> result = sysRoleService.selectPageBeanRoleList(request, "");
    	return getDataTable(result);
	}
	
	@ApiOperation(value="资源管理树形", notes="查询资源树形结构", httpMethod="POST")
	@RequestMapping(value = "/treeresource", method = RequestMethod.POST)
    public @ResponseBody ResultMessage treeResource(HttpServletRequest request, String roleId) {
    	List<Tree> result = sysRoleService.selectResourceListTreeByRoleId(roleId);
    	return new ResultMessage().success(ResultEnum.SUCCESS).addData("resource", result);
	}
	
	@ApiOperation(value="保存修改角色", notes="保存修改角色方法", httpMethod="POST")
	@RequestMapping(value = "/saveorupdaterole", method = RequestMethod.POST)
    public @ResponseBody ResultMessage saveOrUpdateRole(HttpServletRequest request, SysRoleDto sysRoleDto) throws Exception {
    	return sysRoleService.saveOrUpdateSysRole(request, sysRoleDto, "");
	}
	
	@ApiOperation(value="编辑角色页面", notes="跳转到角色编辑页面", httpMethod="POST")
	@RequestMapping(value = "/getrole", method = RequestMethod.POST)
	public @ResponseBody ResultMessage getRole(HttpServletRequest request, String srId) throws Exception {
		SysRoleDto sysRole = sysRoleService.selectSysRoleDtoByRoleId(srId);//根据角色ID查询角色信息
		return new ResultMessage().success(ResultEnum.SUCCESS).addData("role", sysRole);
	}
	
	@ApiOperation(value="删除角色", notes="删除角色方法", httpMethod="POST")
	@RequestMapping(value = "/removerole", method = RequestMethod.POST)
    public @ResponseBody ResultMessage removeRole(HttpServletRequest request, String srId) throws Exception {
    	return sysRoleService.deleteSysRoleByRoleId(srId);
	}
	
	@ApiOperation(value="获取json角色信息", notes="查询角色信息", httpMethod="POST")
	@RequestMapping(value = "/getrolelist", method = RequestMethod.POST)
    public @ResponseBody ResultMessage getOrganizationByPid(HttpServletRequest request) {
    	List<SysRole> rolelist = sysRoleService.selectRoleList();//角色列表
    	return new ResultMessage().success(ResultEnum.SUCCESS).addData("rolelist", rolelist);
	}
}
