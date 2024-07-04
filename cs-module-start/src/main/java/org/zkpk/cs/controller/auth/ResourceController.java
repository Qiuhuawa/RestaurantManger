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
import org.zkpk.cs.common.result.Menu;
import org.zkpk.cs.common.result.ResultEnum;
import org.zkpk.cs.common.result.ResultMessage;
import org.zkpk.cs.common.result.Tree;
import org.zkpk.cs.controller.base.BaseController;
import org.zkpk.cs.entity.SysResource;
import org.zkpk.cs.service.SysResourceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="资源管理", tags = "资源管理")
@Controller
@RequestMapping(value="/res")
public class ResourceController extends BaseController{
	
	@Autowired
	private SysResourceService sysResourceService;
	
	//日志
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    
	private static final String LOGIN_PAGE = "login"; //登录页面
	private static final String LIST_RESOURCE_PAGE = "auth/res/list_resource"; //资源列表页面
	private static final String EDIT_RESOURCE_PAGE = "auth/res/edit_resource"; //修改资源页面
	
	@ApiOperation(value="获取用户菜单", notes="获取用户菜单方法", httpMethod="POST")
	@RequestMapping(value = "/getusermenubyuserid", method = RequestMethod.POST)
	public @ResponseBody ResultMessage getUserMenuByUserId(HttpServletRequest request) {
		List<Menu> result = sysResourceService.selectMenuListTree("1");
		return new ResultMessage().success(ResultEnum.SUCCESS).addData("menu", result);
	}

	
	@ApiOperation(value="资源管理", notes="跳转到资源管理", httpMethod="POST")
	@RequestMapping(value = "/listresource", method = RequestMethod.POST)
	public ModelAndView listResource(ModelAndView mav) {
    	Map<String, Object> dictMap = getMapDictData("RESOURCE_TYPE");//资源类型Map
    	mav.addObject("resourceTypeSelectMap", dictMap);//资源类型Map
	    mav.setViewName(LIST_RESOURCE_PAGE);
	    return mav;
	}
	
	@ApiOperation(value="资源管理列表", notes="查询资源树形列表", httpMethod="POST")
	@RequestMapping(value = "/listresourcedata", method = RequestMethod.POST)
    public @ResponseBody List<SysResource> listResourceData(HttpServletRequest request) {
    	List<SysResource> result = sysResourceService.selectSysResourceList(request, "");
    	return result;
	}
	
	@ApiOperation(value="资源管理树形", notes="查询资源树形结构", httpMethod="POST")
	@RequestMapping(value = "/treeresource", method = RequestMethod.POST)
    public @ResponseBody ResultMessage treeResource(HttpServletRequest request) {
    	List<Tree> result = sysResourceService.selectResourceListTree(request, "");
    	return new ResultMessage().success(ResultEnum.SUCCESS).addData("tree", result);
	}
	
	@ApiOperation(value="保存修改资源", notes="保存修改资源方法", httpMethod="POST")
	@RequestMapping(value = "/saveorupdateresource", method = RequestMethod.POST)
    public @ResponseBody ResultMessage saveOrUpdateResource(SysResource sysResource) throws Exception {
    	return sysResourceService.saveOrUpdateResource(sysResource, "");
	}
	
	@ApiOperation(value="获取权限列表", notes="获取权限列表", httpMethod="GET")
	@RequestMapping(value = "/listurl", method = RequestMethod.GET)
    public @ResponseBody List<Map<String, String>> listUrl() {
    	List<Map<String, String>> result = sysResourceService.selectAllUrlList();
    	return result;
    }
	
	
	@ApiOperation(value="添加编辑资源", notes="跳转到资源添加编辑方法", httpMethod="POST")
	@RequestMapping(value = "/getresource", method = RequestMethod.POST)
	public @ResponseBody ResultMessage getResource(String srId) {
    	SysResource sysResource = new SysResource();
    	if (srId != null) {
    		sysResource = sysResourceService.selectResourceByResourceId(srId);
    	}
    	Map<String, Object> dictMap = getMapDictData("RESOURCE_TYPE");//资源类型Map
    	return new ResultMessage().success(ResultEnum.SUCCESS)
    			.addData("res", sysResource)
    			.addData("resourceTypeSelectMap", dictMap);
	}
	
	@ApiOperation(value="删除资源", notes="删除资源方法", httpMethod="POST")
	@RequestMapping(value = "/removeresource", method = RequestMethod.POST)
    public @ResponseBody ResultMessage removeResource(HttpServletRequest request, String srId) throws Exception {
    	return sysResourceService.deleteResourceByResourceId(srId);
	}
}
