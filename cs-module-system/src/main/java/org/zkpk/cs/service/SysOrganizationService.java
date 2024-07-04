package org.zkpk.cs.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.zkpk.cs.common.result.ResultMessage;
import org.zkpk.cs.common.result.Tree;
import org.zkpk.cs.entity.SysOrganization;

public interface SysOrganizationService {

	List<SysOrganization> selectSysOrganizationList(HttpServletRequest request, String string);

	List<Tree> selectOrganizationListTree();

	ResultMessage saveOrUpdateOrganization(SysOrganization sysOrganization, String string);

	SysOrganization selectOrganizationByOrgId(String soId);

	ResultMessage deleteOrganizationByOrgId(String soId);

}