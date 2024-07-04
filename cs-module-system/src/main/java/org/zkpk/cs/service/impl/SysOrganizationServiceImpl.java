package org.zkpk.cs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zkpk.cs.common.exception.LabException;
import org.zkpk.cs.common.result.ResultEnum;
import org.zkpk.cs.common.result.ResultMessage;
import org.zkpk.cs.common.result.Tree;
import org.zkpk.cs.common.utils.StringUtil;
import org.zkpk.cs.common.utils.TreeUtil;
import org.zkpk.cs.entity.SysOrganization;
import org.zkpk.cs.mapper.SysOrganizationMapper;
import org.zkpk.cs.service.SysOrganizationService;

@Service
public class SysOrganizationServiceImpl implements SysOrganizationService  {

	@Autowired
	private SysOrganizationMapper sysOrganizationMapper;
	
	
	@Override
	public List<SysOrganization> selectSysOrganizationList(HttpServletRequest request, String string) {
		String orgName = request.getParameter("orgName");
		//查询参数信息
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("orgName", orgName);
		List<SysOrganization> oganizationlist = sysOrganizationMapper.selectSysOrganizationListByParameter(parameterMap);
		return oganizationlist;
	}


	@Override
	public List<Tree> selectOrganizationListTree() {
		List<Tree> resultlist = new ArrayList<Tree>();
		//查询参数信息
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		List<SysOrganization> oganizationlist = sysOrganizationMapper.selectSysOrganizationListByParameter(parameterMap);
		for (SysOrganization sysOrganization : oganizationlist) {
			Tree tree = new Tree();
			tree.setId(sysOrganization.getSoId());  //ID
			tree.setParentId(sysOrganization.getParentSoId()); //父ID
			tree.setTitle(sysOrganization.getOrgName()); //名称
			tree.setText(sysOrganization.getOrgName()); //名称
			tree.setAttributes(sysOrganization);
			resultlist.add(tree);
		}
		resultlist = TreeUtil.buildTreeBy2Loop(resultlist, "");
		return resultlist;
	}


	@Override
	public ResultMessage saveOrUpdateOrganization(SysOrganization sysOrganization, String string) {
		Date dt = new Date();//系统当前时间
		if (sysOrganization.getSoId() != null && !"".equals(sysOrganization.getSoId())) {
			String parentSoId = sysOrganization.getParentSoId();
			if (parentSoId != null && !"".equals(parentSoId)) {
				sysOrganization.setOrgType("ORG_TYPE_GROUP");
			} else {
				sysOrganization.setOrgType("ORG_TYPE_SCHOOL");
			}
			sysOrganization.setUpdateBy("");//变更者
			sysOrganization.setUpdateTime(dt);//变更时间
			//修改组织
			int resultFlag = sysOrganizationMapper.updateByPrimaryKeySelective(sysOrganization);
			if (resultFlag == 1) {//修改成功
				return new ResultMessage().success(ResultEnum.EDIT_SUCCESS);
			} else {
				throw new LabException(ResultEnum.EDIT_FAILED);
			}
		} else {
			String soId = StringUtil.getDateUUId();
			sysOrganization.setSoId(soId);//主键
			String parentSoId = sysOrganization.getParentSoId();
			if (parentSoId != null && !"".equals(parentSoId)) {
				sysOrganization.setOrgType("ORG_TYPE_GROUP");
			} else {
				sysOrganization.setOrgType("ORG_TYPE_SCHOOL");
			}
			sysOrganization.setCreateBy("");//作成者
			sysOrganization.setCreateTime(dt);//作成时间
			//添加组织
			int resultFlag = sysOrganizationMapper.insertSelective(sysOrganization);
			if (resultFlag == 1) {//添加成功
				return new ResultMessage().success(ResultEnum.ADD_SUCCESS);
			} else {
				throw new LabException(ResultEnum.ADD_FAILED);
			}
		}
	}


	@Override
	public SysOrganization selectOrganizationByOrgId(String soId) {
		SysOrganization sysOrganization = new SysOrganization();
		if (StringUtil.isNotNull(soId)) {
			sysOrganization = sysOrganizationMapper.selectByPrimaryKey(soId);
		}
		return sysOrganization;
	}


	@Override
	public ResultMessage deleteOrganizationByOrgId(String soId) {
		//查询参数信息
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("parentSoId", soId);//父code
		List<SysOrganization> organizationlist = sysOrganizationMapper.selectSysOrganizationListByParameter(parameterMap);
		if (organizationlist != null && organizationlist.size() > 0) {
			//该节点存在子集，不能删除
			throw new LabException(ResultEnum.CANNOT_DELETED);
		} else {
			//删除组织
			int resultFlag = sysOrganizationMapper.deleteByPrimaryKey(soId);
			if (resultFlag == 1) {//删除成功
				return new ResultMessage().success(ResultEnum.DELETE_SUCCESS);
			} else {
				throw new LabException(ResultEnum.DELETE_FAILED);
			}
		}
	}

}
