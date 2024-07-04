package org.zkpk.cs.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.scheduling.annotation.Async;
import org.zkpk.cs.common.result.PageBean;
import org.zkpk.cs.common.result.ResultMessage;
import org.zkpk.cs.dto.SysLogDto;
import org.zkpk.cs.entity.SysLogWithBLOBs;

public interface SysLogService {
	
	@Async
	public void saveSysLogWithBLOBs(SysLogWithBLOBs sysLogWithBLOBs);

	public PageBean<SysLogDto> selectPageBeanSysLogDtoList(HttpServletRequest request, String userId);

	public ResultMessage deleteSysLogBySlIds(String slIds, String userId);

}
