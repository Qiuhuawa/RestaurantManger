package org.zkpk.cs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.zkpk.cs.common.exception.LabException;
import org.zkpk.cs.common.result.Menu;
import org.zkpk.cs.common.result.ResultEnum;
import org.zkpk.cs.common.result.ResultMessage;
import org.zkpk.cs.common.result.Tree;
import org.zkpk.cs.common.utils.StringUtil;
import org.zkpk.cs.common.utils.TreeUtil;
import org.zkpk.cs.dto.SysResourceDto;
import org.zkpk.cs.entity.SysResource;
import org.zkpk.cs.mapper.SysResourceMapper;
import org.zkpk.cs.service.SysResourceService;

@Service
public class SysResourceServiceImpl implements SysResourceService{
	
	@Autowired
	private SysResourceMapper sysResourceMapper;
	
	@Autowired
	private WebApplicationContext applicationContext;

	@Override
	public List<SysResource> selectSysResourceList(HttpServletRequest request, String string) {
		String resourceName = request.getParameter("resourceName");
		String resourceType = request.getParameter("resourceType");
		//查询参数信息
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("resourceName", resourceName);
		parameterMap.put("resourceType", resourceType);
		List<SysResource> resourcelist = sysResourceMapper.selectSysResourceListByParameter(parameterMap);
		return resourcelist;
	}

	@Override
	public List<Tree> selectResourceListTree(HttpServletRequest request, String string) {
		List<Tree> resultlist = new ArrayList<Tree>();
		//查询参数信息
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		List<SysResource> resourcelist = sysResourceMapper.selectSysResourceListByParameter(parameterMap);
		for (SysResource sysResource : resourcelist) {
			Tree tree = new Tree();
			tree.setId(sysResource.getSrId());  //ID
			tree.setParentId(sysResource.getParentSrId()); //父ID
			tree.setTitle(sysResource.getResourceName()); //名称
			tree.setText(sysResource.getResourceName()); //名称
			tree.setAttributes(sysResource);
			resultlist.add(tree);
		}
		resultlist = TreeUtil.buildTreeBy2Loop(resultlist, "");
		return resultlist;
	}

	@Override
	public ResultMessage saveOrUpdateResource(SysResource sysResource, String string) {
		Date dt = new Date();//系统当前时间
		String resourceCode = sysResource.getResourceCode().toUpperCase();//资源编码
		//修改
		if (sysResource.getSrId() != null && !"".equals(sysResource.getSrId())) {
			SysResource result = sysResourceMapper.selectByPrimaryKey(sysResource.getSrId());
			if (!result.getResourceCode().equals(resourceCode)) {
				Map<String ,Object> parameterMap = new HashMap<String ,Object>();
				parameterMap.put("resourceCode", resourceCode);//资源编码
				List<SysResource> sysresourcelist = sysResourceMapper.selectSysResourceListByParameter(parameterMap);
				if (sysresourcelist != null && sysresourcelist.size() > 0) {
					throw new LabException(ResultEnum.CANNOT_ADD_CODE_EXIST);//编码存在
				}
				sysResource.setResourceCode(resourceCode);//资源编码
			}
			sysResource.setUpdateBy("");//变更者
			sysResource.setUpdateTime(dt);//变更时间
			//修改资源
			int resultFlag = sysResourceMapper.updateByPrimaryKeySelective(sysResource);
			if (resultFlag == 1) {//修改成功
				return new ResultMessage().success(ResultEnum.EDIT_SUCCESS);
			} else {
				throw new LabException(ResultEnum.EDIT_FAILED);
			}
		} else {
			Map<String ,Object> parameterMap = new HashMap<String ,Object>();
			parameterMap.put("resourceCode", resourceCode);//资源编码
			List<SysResource> sysresourcelist = sysResourceMapper.selectSysResourceListByParameter(parameterMap);
			if (sysresourcelist != null && sysresourcelist.size() > 0) {
				throw new LabException(ResultEnum.CANNOT_ADD_CODE_EXIST);//编码存在
			}
			String srId = StringUtil.getDateUUId();
			sysResource.setSrId(srId);//主键
			sysResource.setResourceCode(resourceCode);//资源编码
			sysResource.setCreateBy("");//作成者
			sysResource.setCreateTime(dt);//作成时间
			//添加资源
			int resultFlag = sysResourceMapper.insertSelective(sysResource);
			if (resultFlag == 1) {//添加成功
				return new ResultMessage().success(ResultEnum.ADD_SUCCESS);
			} else {
				throw new LabException(ResultEnum.ADD_FAILED);
			}
		}
	}

	@Override
	public List<Map<String, String>> selectAllUrlList() {
		RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        //获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<Map<String, String>> urlList = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            RequestMappingInfo info = entry.getKey();
            HandlerMethod handlerMethod = map.get(info);
            RequiresPermissions permissions = handlerMethod.getMethodAnnotation(RequiresPermissions.class);
            String perms = "";
            if (null != permissions) {
                perms = StringUtils.join(permissions.value(), ",");
            }
            Set<String> patterns = info.getPatternsCondition().getPatterns();
            for (String url : patterns) {
                Map<String, String> urlMap = new HashMap<>();
                urlMap.put("url", url.replaceFirst("\\/", ""));
                urlMap.put("perms", perms);
                urlList.add(urlMap);
            }
        }
        return urlList;
	}

	@Override
	public SysResource selectResourceByResourceId(String srId) {
		SysResource sysResource = sysResourceMapper.selectByPrimaryKey(srId);
		return sysResource;
	}

	@Override
	public ResultMessage deleteResourceByResourceId(String srId) {
		//查询参数信息
		Map<String ,Object> parameterMap = new HashMap<String ,Object>();
		parameterMap.put("parentSrId", srId);//父ID
		List<SysResource> resourcelist = sysResourceMapper.selectSysResourceListByParameter(parameterMap);
		if (resourcelist != null && resourcelist.size() > 0) {
			//该节点存在子集，不能删除
			throw new LabException(ResultEnum.CANNOT_DELETED);
		} else {
			//删除资源
			int resultFlag = sysResourceMapper.deleteByPrimaryKey(srId);
			if (resultFlag == 1) {//删除成功
				return new ResultMessage().success(ResultEnum.DELETE_SUCCESS);
			} else {
				throw new LabException(ResultEnum.DELETE_FAILED);
			}
		}
	}

	@Override
	public List<Menu> selectMenuListTree(String userId) {
		List<Menu> resultlist = new ArrayList<Menu>();
		List<SysResourceDto> resourcelist = new ArrayList<SysResourceDto>();
		if ("1".equals(userId)) {
			resourcelist = sysResourceMapper.selectAdminMenuResourceDto();
		} else {
			resourcelist = sysResourceMapper.selectMenuResourceDtoByUserId(userId);
		}
		for (SysResourceDto sysResourceDto : resourcelist) {
			Menu menu = new Menu();
			menu.setId(sysResourceDto.getSrId());
			menu.setPid(sysResourceDto.getParentSrId());
			menu.setName(sysResourceDto.getResourceName());
			menu.setRedirect(sysResourceDto.getUrl());
			menu.setPath(sysResourceDto.getUrl());
			menu.setIcon(sysResourceDto.getIcon());
			resultlist.add(menu);
		}
		resultlist = makeMenuTree(resultlist);
		return resultlist;
	}
	
	private List<Menu> makeMenuTree(List<Menu> menulist) {
		List<Menu> parent = new ArrayList<Menu>();
		// get parentId = null;
		for (Menu menu : menulist) {
			if (menu.getPid() == null || "".equals(menu.getPid())) {
				menu.setChildren(new ArrayList<Menu>());
				parent.add(menu);
			} 
		}
		// 删除parentId = null;
		menulist.removeAll(parent);
		makeMenuChildren(parent, menulist);
		return parent;
	}
	
	
	private void makeMenuChildren(List<Menu> parent, List<Menu> children) {
		if (children.isEmpty()) {
			return;
		}
		List<Menu> tmp = new ArrayList<Menu>();
		for (Menu c1 : parent) {
			for (Menu c2 : children) {
				//c2.setChildren(new ArrayList<Menu>());
				if (c1.getId().equals(c2.getPid())) {
					c1.getChildren().add(c2);
					tmp.add(c2);
				}
			}
		}
		children.removeAll(tmp);
		makeMenuChildren(tmp, children);
	}

	@Override
	public List<SysResourceDto> selectMenuResourceDtoByUserId(String userId) {
		List<SysResourceDto> resultlist = new ArrayList<SysResourceDto>();
		if ("1".equals(userId)) {
			resultlist = sysResourceMapper.selectAdminMenuResourceDto();
		} else {
			resultlist = sysResourceMapper.selectMenuResourceDtoByUserId(userId);
		}
		resultlist = makeTree(resultlist);
		return resultlist;
	}
	
	private List<SysResourceDto> makeTree(List<SysResourceDto> menulist) {
		List<SysResourceDto> parent = new ArrayList<SysResourceDto>();
		// get parentId = null;
		for (SysResourceDto resourceDto : menulist) {
			if (resourceDto.getParentSrId() == null || "".equals(resourceDto.getParentSrId())) {
				resourceDto.setChildren(new ArrayList<SysResourceDto>(0));
				parent.add(resourceDto);
			}
		}
		// 删除parentId = null;
		menulist.removeAll(parent);
		makeChildren(parent, menulist);
		return parent;
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @author HUCHAO
	 * @date 2018-05-03 10:53:32
	 * @param parent
	 * @param children
	 */
	private void makeChildren(List<SysResourceDto> parent, List<SysResourceDto> children) {
		if (children.isEmpty()) {
			return;
		}
		List<SysResourceDto> tmp = new ArrayList<SysResourceDto>();
		for (SysResourceDto c1 : parent) {
			for (SysResourceDto c2 : children) {
				c2.setChildren(new ArrayList<SysResourceDto>(0));
				if (c1.getSrId().equals(c2.getParentSrId())) {
					c1.getChildren().add(c2);
					tmp.add(c2);
				}
			}
		}
		children.removeAll(tmp);
		makeChildren(tmp, children);
	}

	@Override
	public SysResource selectMenuResourceByRightParentTrId(String parentSrId) {
		SysResource sysResource = sysResourceMapper.selectMenuResourceByRightParentTrId(parentSrId);
		return sysResource;
	}

}
