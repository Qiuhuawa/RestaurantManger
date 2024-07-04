package org.zkpk.cs.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkpk.cs.common.result.Menu;
import org.zkpk.cs.common.result.ResultMessage;
import org.zkpk.cs.common.result.Tree;
import org.zkpk.cs.dto.SysResourceDto;
import org.zkpk.cs.entity.SysResource;

public interface SysResourceService {

	List<SysResource> selectSysResourceList(HttpServletRequest request, String string);

	List<Tree> selectResourceListTree(HttpServletRequest request, String string);

	ResultMessage saveOrUpdateResource(SysResource sysResource, String string);

	List<Map<String, String>> selectAllUrlList();

	SysResource selectResourceByResourceId(String srId);

	ResultMessage deleteResourceByResourceId(String srId);

	List<Menu> selectMenuListTree(String string);

	List<SysResourceDto> selectMenuResourceDtoByUserId(String suId);

	SysResource selectMenuResourceByRightParentTrId(String parentSrId);


}
