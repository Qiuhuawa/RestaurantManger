package org.zkpk.cs.controller.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkpk.cs.common.result.PageBean;
import org.zkpk.cs.entity.SysDictionary;
import org.zkpk.cs.service.SysDictionaryService;
import org.zkpk.cs.shiro.ShiroUser;

public class BaseController {
	
	@Autowired
    private SysDictionaryService sysDictionaryService;
	
	
	 public List<SysDictionary> getDictData(String targetCode) {
	        List<SysDictionary> dictlist = sysDictionaryService.selectDictDataByTargetCode(targetCode);
	        return dictlist;
	    }
	 
	 public List<SysDictionary> getDictData(String targetCode, String removeCode) {
	        List<SysDictionary> dictlist = sysDictionaryService.selectDictDataByTargetCodeWithOutRemoveCode(targetCode,removeCode);
	        return dictlist;
	    }
	 
	 
	 public Map<String, Object> getMapDictData(String targetCode) {
	    	Map<String, Object> resultMap = sysDictionaryService.selectMapDictDataByTargetCode(targetCode);
	        return resultMap;
	    }
	 
	 
	 public Map<String, Object> getMapDictData(String targetCode, String removeCode) {
	    	Map<String, Object> resultMap = sysDictionaryService.selectMapDictDataByTargetCodeWithOutRemoveCode(targetCode, removeCode);
	        return resultMap;
	    }
	 
	 
	 /**
	     * 
	     * @Description: 封装成前端格式
	     * @author HUCHAO
	     * @date 2019-01-16 10:21:39
	     * @param pageBean
	     * @return
	     */
	    public Map<String, Object> getDataTable(PageBean<?> pageBean) {
			Map<String, Object> rspData = new HashMap<>();
			rspData.put("rows", pageBean.getList());
			rspData.put("total", pageBean.getTotal());
			return rspData;
		}
	    
	    /**
	     * 
	     * @Description: 获取当前用户
	     * @author HUCHAO
	     * @date 2018-04-26 14:25:48
	     * @param subject
	     * @return
	     */
	    public ShiroUser getShiroUser(Subject subject) {
	        return (ShiroUser) subject.getPrincipal();
	    }
	    
	    /**
	     * 
	     * @Description: 获取当前用户ID
	     * @author HUCHAO
	     * @date 2018-04-26 14:25:35
	     * @param subject
	     * @return
	     */
	    public String getShiroUserId(Subject subject) {
	    	ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
	    	String userId = shiroUser.getId();
	        return userId;
	    }

}
