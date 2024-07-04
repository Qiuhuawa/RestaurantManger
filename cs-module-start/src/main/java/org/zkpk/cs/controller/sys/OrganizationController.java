package org.zkpk.cs.controller.sys;

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
import org.zkpk.cs.common.result.ResultEnum;
import org.zkpk.cs.common.result.ResultMessage;
import org.zkpk.cs.common.result.Tree;
import org.zkpk.cs.controller.base.BaseController;
import org.zkpk.cs.entity.SysOrganization;
import org.zkpk.cs.service.SysOrganizationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="组织管理", tags = "组织管理")
@Controller
@RequestMapping(value="/org")
public class OrganizationController extends BaseController{
	
	@Autowired
	private SysOrganizationService sysOrganizationService;
	
	//日志
    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);
    
	private static final String LOGIN_PAGE = "login"; //登录页面
	private static final String LIST_ORG_PAGE = "auth/org/list_organization"; //资源组织页面
	private static final String EDIT_ORG_PAGE = "auth/org/edit_organization"; //修改组织页面
	
	
	@ApiOperation(value="组织管理", notes="跳转到组织管理", httpMethod="POST")
	@RequestMapping(value = "/listorganization", method = RequestMethod.POST)
	public ModelAndView listOrganization(ModelAndView mav) {
    	Map<String, Object> dictMap = getMapDictData("ORG_TYPE");
		mav.addObject("orgTypeMap", dictMap);//当前组织类型Map
	    mav.setViewName(LIST_ORG_PAGE);
	    return mav;
	}
	
	@ApiOperation(value="组织管理列表", notes="查询组织树形列表", httpMethod="POST")
	@RequestMapping(value = "/listorganizationdata", method = RequestMethod.POST)
    public @ResponseBody List<SysOrganization> listOrganizationData(HttpServletRequest request) {
    	List<SysOrganization> result = sysOrganizationService.selectSysOrganizationList(request, "");
    	return result;
	}
	
	@ApiOperation(value="组织管理树形", notes="查询组织树形结构", httpMethod="POST")
	@RequestMapping(value = "/treeorganization", method = RequestMethod.POST)
    public @ResponseBody ResultMessage treeOrganization(HttpServletRequest request) {
    	List<Tree> result = sysOrganizationService.selectOrganizationListTree();
    	return new ResultMessage().success(ResultEnum.SUCCESS).addData("org", result);
	}
	
	@ApiOperation(value="保存修改组织", notes="保存修改组织方法", httpMethod="POST")
	@RequestMapping(value = "/saveorupdateorganization", method = RequestMethod.POST)
    public @ResponseBody ResultMessage saveOrUpdateResource(SysOrganization sysOrganization) throws Exception {
    	return sysOrganizationService.saveOrUpdateOrganization(sysOrganization, "");
	}
	
	@ApiOperation(value="添加编辑组织页面", notes="跳转到组织添加编辑页面", httpMethod="POST")
	@RequestMapping(value = "/getorganization", method = RequestMethod.POST)
	public @ResponseBody ResultMessage getOrganization(String soId) {
		SysOrganization sysOrganization = sysOrganizationService.selectOrganizationByOrgId(soId);
		return new ResultMessage().success(ResultEnum.SUCCESS).addData("org", sysOrganization);//组织
	}
	
	@ApiOperation(value="删除组织", notes="删除组织方法", httpMethod="POST")
	@RequestMapping(value = "/removeorganization", method = RequestMethod.POST)
    public @ResponseBody ResultMessage removeOrganization(HttpServletRequest request, String soId) throws Exception {
    	return sysOrganizationService.deleteOrganizationByOrgId(soId);
	}

}
