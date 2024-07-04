package org.zkpk.cs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zkpk.cs.common.exception.LabException;
import org.zkpk.cs.common.result.PageBean;
import org.zkpk.cs.common.result.ResultEnum;
import org.zkpk.cs.common.result.ResultMessage;
import org.zkpk.cs.common.utils.DateUtil;
import org.zkpk.cs.common.utils.StringUtil;
import org.zkpk.cs.common.utils.TimestampUtil;
import org.zkpk.cs.dto.SysLogDto;
import org.zkpk.cs.entity.SysLogWithBLOBs;
import org.zkpk.cs.mapper.SysLogMapper;
import org.zkpk.cs.service.SysLogService;

import com.github.pagehelper.PageHelper;

@Service
public class SysLogServiceImpl implements SysLogService {
	
	@Autowired
    private SysLogMapper sysLogMapper;
	
	//日志
	private static final Logger logger = LoggerFactory.getLogger(SysLogServiceImpl.class);
    
	@Override
	public void saveSysLogWithBLOBs(SysLogWithBLOBs sysLogWithBLOBs) {
		sysLogMapper.insertSelective(sysLogWithBLOBs);
	}

	@Override
	public PageBean<SysLogDto> selectPageBeanSysLogDtoList(HttpServletRequest request, String userId) {
		String pnum = request.getParameter("pageNum");
    	String psize = request.getParameter("pageSize");
		int pageNum = Integer.valueOf(pnum == null ? "1" : pnum);// 当前页
		int pageSize = Integer.valueOf(psize == null ? "10" : psize);// 每页数量	
		String requestTime = request.getParameter("requestTime");//操作时间
		String startTime = "";
		String endTime = "";
		if (!StringUtil.isEmpty(requestTime)) {
			startTime = requestTime.split(" -- ")[0];
			endTime = requestTime.split(" -- ")[1];		
		}
		String realName = request.getParameter("realName");//用户名
		String operation = request.getParameter("operation");//描述
		List<SysLogDto> loglist = null; //查询结果集合
		Map<String, Object> parameterMap = new HashMap<String, Object>(); //参数map
		parameterMap.put("startTime", startTime);
		parameterMap.put("endTime", endTime);
		parameterMap.put("realName", realName);
		parameterMap.put("operation", operation);
		PageHelper.startPage(pageNum, pageSize);
		loglist = sysLogMapper.selectSysLogListByParameter(parameterMap); //查询集合
		return new PageBean<SysLogDto>(loglist);
	}

	@Override
	public ResultMessage deleteSysLogBySlIds(String slIds, String userId) {
		String[] logIds = slIds.split(",");//资源
		int resultFlag = sysLogMapper.batchDeleteByLogIds(logIds);
		if (resultFlag == logIds.length) {//删除成功
			return new ResultMessage().success(ResultEnum.DELETE_SUCCESS);
		} else {
			throw new LabException(ResultEnum.DELETE_FAILED);
		}
	}

}
