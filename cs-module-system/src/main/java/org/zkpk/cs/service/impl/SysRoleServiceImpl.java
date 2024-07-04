package org.zkpk.cs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zkpk.cs.common.exception.LabException;
import org.zkpk.cs.common.result.PageBean;
import org.zkpk.cs.common.result.ResultEnum;
import org.zkpk.cs.common.result.ResultMessage;
import org.zkpk.cs.common.result.Tree;
import org.zkpk.cs.common.utils.CopyUtil;
import org.zkpk.cs.common.utils.StringUtil;
import org.zkpk.cs.common.utils.TreeUtil;
import org.zkpk.cs.dto.SysRoleDto;
import org.zkpk.cs.dto.SysRoleResourceDto;
import org.zkpk.cs.dto.SysUserRoleDto;
import org.zkpk.cs.entity.SysResource;
import org.zkpk.cs.entity.SysRole;
import org.zkpk.cs.entity.SysRoleResource;
import org.zkpk.cs.mapper.SysResourceMapper;
import org.zkpk.cs.mapper.SysRoleMapper;
import org.zkpk.cs.mapper.SysRoleResourceMapper;
import org.zkpk.cs.mapper.SysUserRoleMapper;
import org.zkpk.cs.service.SysRoleService;

import com.github.pagehelper.PageHelper;

@Service
public class SysRoleServiceImpl implements SysRoleService  {
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private SysRoleResourceMapper sysRoleResourceMapper;
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	
	@Autowired
	private SysResourceMapper sysResourceMapper;

	@Override
	public PageBean<SysRole> selectPageBeanRoleList(HttpServletRequest request, String string) {
		String pnum = request.getParameter("pageNum");
    	String psize = request.getParameter("pageSize");
		int pageNum = Integer.valueOf(pnum == null ? "1" : pnum);// 当前页
		int pageSize = Integer.valueOf(psize == null ? "10" : psize);// 每页数量	
		String roleName = request.getParameter("roleName");//角色名
		Map<String ,Object> parameterMap = new HashMap<String, Object>(); //参数map
		parameterMap.put("roleName", roleName);
		//开始分页：PageHelper会处理接下来的第一个查询  
		PageHelper.startPage(pageNum, pageSize);
		List<SysRole> rolelist = sysRoleMapper.selectRoleListByParameter(parameterMap); //查询集合
		return new PageBean<SysRole>(rolelist);
	}

	@Override
	public List<Tree> selectResourceListTreeByRoleId(String roleId) {
		//角色资源MAP
		Map<String, Object> roleResourceMap = new HashMap<String, Object>();
		//查询角色资源
		if (roleId != null && !"".equals(roleId)) {
			//根据角色ID查询角色资源信息
			List<SysRoleResourceDto> roleresourcelist = sysRoleResourceMapper.selectRoleResourceListByRoleId(roleId);
			for (SysRoleResourceDto sysRoleResourceDto : roleresourcelist) {
				roleResourceMap.put(sysRoleResourceDto.getResource().getSrId(), sysRoleResourceDto);
			}
		}
		//查询参数信息
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		List<SysResource> resourcelist = sysResourceMapper.selectSysResourceListByParameter(parameterMap);
		List<Tree> resultlist = new ArrayList<Tree>();//封装树形返回对象
		//封装返回树形
		for (SysResource sysResource : resourcelist) {
			Tree tree = new Tree();
			tree.setId(sysResource.getSrId());  //ID
			tree.setParentId(sysResource.getParentSrId()); //父ID
			String description = StringUtil.isNotNull(sysResource.getDescription()) == true ? sysResource.getDescription() : "-";//描述
			tree.setTitle(sysResource.getResourceName() + "【" + description + "】"); //名称
			if (roleResourceMap.get(sysResource.getSrId()) != null) {
				tree.setChecked(true);//选中
			}
			tree.setText(sysResource.getResourceName() + "【" + description + "】"); //名称
			tree.setAttributes(sysResource);
			resultlist.add(tree);
		}
		resultlist = TreeUtil.buildTreeBy2Loop(resultlist, "");
		return resultlist;
	}

	@Override
	public ResultMessage saveOrUpdateSysRole(HttpServletRequest request, SysRoleDto sysRoleDto, String string) throws Exception {
		SysRole sysRole = new SysRole();
		CopyUtil.copyBeans(sysRoleDto, sysRole);
		Date dt = new Date();//系统当前时间
		String resourceIdStr = sysRoleDto.getResourceIdStr();//获取资源ID字符串
		String[] resourceIds = resourceIdStr.split(",");//资源
		String roleCode = sysRole.getRoleCode().toUpperCase();//角色编码
		//修改
		if (sysRole.getSrId() != null && !"".equals(sysRole.getSrId())) {
			SysRole result = sysRoleMapper.selectByPrimaryKey(sysRole.getSrId());
			if (!result.getRoleCode().equals(roleCode)) {//编码不一致去查询新的编码是否存在
				Map<String ,Object> parameterMap = new HashMap<String ,Object>();
				parameterMap.put("roleCode", roleCode);//角色编码
				List<SysRole> sysrolelist = sysRoleMapper.selectRoleListByParameter(parameterMap);
				if (sysrolelist != null && sysrolelist.size() > 0) {
					throw new LabException(ResultEnum.CANNOT_ADD_CODE_EXIST);//编码存在
				}
				sysRole.setRoleCode(roleCode);//资源编码
			}
			sysRole.setUpdateBy("");//变更者
			sysRole.setUpdateTime(dt);//变更时间
			//修改资源
			int resultFlag = sysRoleMapper.updateByPrimaryKeySelective(sysRole);
			//封装删除ID
			List<String> delidlist = new ArrayList<String>();
			//查询角色对应资源
			List<SysRoleResourceDto> resultlist = sysRoleResourceMapper.selectRoleResourceListByRoleId(sysRole.getSrId());
			for (SysRoleResourceDto sysRoleResourceDto : resultlist) {
				delidlist.add(sysRoleResourceDto.getSrrId());
			}
			if (delidlist != null && delidlist.size() > 0 ) {
				sysRoleResourceMapper.batchDeleteSysRoleResource(delidlist);
			}
			//角色资源ID
			if (resourceIds.length > 0) {
				//用于封装SysRoleResource对象
				List<SysRoleResource> newsysroleresourcelist = new ArrayList<SysRoleResource>();
				for (String resourceId : resourceIds) {
					SysRoleResource sysRoleResource = new SysRoleResource();
					String srrId = StringUtil.getDateUUId();
					sysRoleResource.setSrrId(srrId);
					sysRoleResource.setRoleId(sysRole.getSrId());//角色ID
					sysRoleResource.setResourceId(resourceId);//资源ID
					sysRoleResource.setResourceScope("1");//权限使用范围（预留权限扩展使用）
					sysRoleResource.setCreateBy("");//作成者
					sysRoleResource.setCreateTime(dt);//作成时间
					newsysroleresourcelist.add(sysRoleResource);
				}
				//批量插入角色资源
				int resultBatchFlag = 0;
				if (newsysroleresourcelist != null && newsysroleresourcelist.size() > 0) {
					resultBatchFlag = sysRoleResourceMapper.batchInsertSysRoleResource(newsysroleresourcelist);
				}
				if (resultFlag == 1 && resultBatchFlag == resourceIds.length) {//添加成功
					return new ResultMessage().success(ResultEnum.EDIT_SUCCESS);
				} else {
					throw new LabException(ResultEnum.EDIT_FAILED);
				}
			} else {
				if (resultFlag == 1) {//修改成功
					return new ResultMessage().success(ResultEnum.EDIT_SUCCESS);
				} else {
					throw new LabException(ResultEnum.EDIT_FAILED);
				}
			}
		} else {
			Map<String ,Object> parameterMap = new HashMap<String ,Object>();
			parameterMap.put("roleCode", roleCode);//角色编码
			List<SysRole> sysrolelist = sysRoleMapper.selectRoleListByParameter(parameterMap);
			if (sysrolelist != null && sysrolelist.size() > 0) {
				throw new LabException(ResultEnum.CANNOT_ADD_CODE_EXIST);//编码存在
			}
			String srId = StringUtil.getDateUUId();
			sysRole.setSrId(srId);//主键
			sysRole.setRoleCode(roleCode);//角色编码
			sysRole.setCreateBy("");//作成者
			sysRole.setCreateTime(dt);//作成时间
			//添加资源
			int resultFlag = sysRoleMapper.insertSelective(sysRole);
			//角色资源ID
			if (resourceIds.length > 0) {
				//用于封装SysRoleResource对象
				List<SysRoleResource> newsysroleresourcelist = new ArrayList<SysRoleResource>();
				for (String resourceId : resourceIds) {
					SysRoleResource sysRoleResource = new SysRoleResource();
					String srrId = StringUtil.getDateUUId();
					sysRoleResource.setSrrId(srrId);
					sysRoleResource.setRoleId(srId);//角色ID
					sysRoleResource.setResourceId(resourceId);//资源ID
					sysRoleResource.setResourceScope("1");//权限使用范围（预留权限扩展使用）
					sysRoleResource.setCreateBy("");//作成者
					sysRoleResource.setCreateTime(dt);//作成时间
					newsysroleresourcelist.add(sysRoleResource);
				}
				//批量插入角色资源
				int resultBatchFlag = 0;
				if (newsysroleresourcelist != null && newsysroleresourcelist.size() > 0) {
					resultBatchFlag = sysRoleResourceMapper.batchInsertSysRoleResource(newsysroleresourcelist);
				}
				if (resultFlag == 1 && resultBatchFlag == resourceIds.length) {//添加成功
					return new ResultMessage().success(ResultEnum.ADD_SUCCESS);
				} else {
					throw new LabException(ResultEnum.ADD_FAILED);
				}
			} else {
				if (resultFlag == 1) {//添加成功
					return new ResultMessage().success(ResultEnum.ADD_SUCCESS);
				} else {
					throw new LabException(ResultEnum.ADD_FAILED);
				}
			}
		}
	}

	@Override
	public SysRoleDto selectSysRoleDtoByRoleId(String srId) throws Exception {
		SysRoleDto sysRoleDto = new SysRoleDto();
		if (StringUtil.isNotNull(srId)) {
			SysRole sysRole = sysRoleMapper.selectByPrimaryKey(srId);
			CopyUtil.copyBeans(sysRole, sysRoleDto);
			List<SysRoleResourceDto> roleresourcelist = sysRoleResourceMapper.selectRoleResourceListByRoleId(srId);
			if (roleresourcelist.size() > 0) {
				String[] resourceIds = new String[roleresourcelist.size()];
				int count = 0;
				for (SysRoleResourceDto sysRoleResourceDto : roleresourcelist) {
					resourceIds[count] = sysRoleResourceDto.getResource().getSrId();
					count++;
				} 
				sysRoleDto.setResourceIds(resourceIds);
			}
		}
		return sysRoleDto;
	}

	@Override
	public ResultMessage deleteSysRoleByRoleId(String srId) {
		List<SysUserRoleDto> userrolelist = sysUserRoleMapper.selectUserRoleListByRoleId(srId);
		if (userrolelist != null && userrolelist.size() > 0) {
			//该角色下存在用户，不能删除
			throw new LabException(ResultEnum.CANNOT_DELETED_HASUSER);
		} else {
			//删除角色
			int resultFlag = sysRoleMapper.deleteByPrimaryKey(srId);
			if (resultFlag == 1) {//删除成功
				return new ResultMessage().success(ResultEnum.DELETE_SUCCESS);
			} else {
				throw new LabException(ResultEnum.DELETE_FAILED);
			}
		}
	}

	@Override
	public Map<String, SysRole> selectShowRoleMap() {
		Map<String, SysRole> resultMap = new LinkedHashMap<String, SysRole>(); //结果map
		Map<String, Object> parameterMap = new HashMap<String, Object>(); //参数map
		parameterMap.put("isShow", "1");//是否显示  1表示显示
		List<SysRole> rolelist = sysRoleMapper.selectRoleListByParameter(parameterMap);
		for (SysRole sysRole : rolelist) {
			resultMap.put(sysRole.getSrId(), sysRole);
		}
		return resultMap;
	}

	@Override
	public List<SysRole> selectRoleList() {
		Map<String, Object> parameterMap = new HashMap<String, Object>(); //参数map
		List<SysRole> rolelist = sysRoleMapper.selectRoleListByParameter(parameterMap);
		return rolelist;
	}

	@Override
	public List<SysRoleResourceDto> selectRoleResourceListByRoleId(String roleId) {
		List<SysRoleResourceDto> roleResourceList = sysRoleResourceMapper.selectRoleResourceListByRoleId(roleId);
		return roleResourceList;
	}

}
