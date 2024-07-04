package org.zkpk.cs.controller.sys;

import java.util.List;

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
import org.zkpk.cs.entity.SysDictionary;
import org.zkpk.cs.service.SysDictionaryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="字典管理", tags = "字典管理")
@Controller
@RequestMapping(value="/dict")
public class DictionaryController {

	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	
    private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);
    
	private static final String LOGIN_PAGE = "login"; //登录页面
	private static final String LIST_DICT_PAGE = "sys/dict/list_dict"; //数据字典页面

	@ApiOperation(value="字典管理", notes="跳转到字典管理", httpMethod="POST")
	@RequestMapping(value = "/listdict", method = RequestMethod.POST)
	public ModelAndView listDict(ModelAndView mav) {
	    mav.setViewName(LIST_DICT_PAGE);
	    return mav;
	}
	
	@ApiOperation(value="字典管理列表", notes="查询字典树形列表", httpMethod="POST")
	@RequestMapping(value = "/listdictdata", method = RequestMethod.POST)
    public @ResponseBody List<SysDictionary> listDictData(HttpServletRequest request) {
    	List<SysDictionary> result = sysDictionaryService.selectSysDictionaryList(request, "");
    	return result;
	}
	
	@ApiOperation(value="字典管理树形", notes="查询字典树形结构", httpMethod="POST")
	@RequestMapping(value = "/treedict", method = RequestMethod.POST)
    public @ResponseBody ResultMessage treeDict(String parentSdId){
    	List<Tree> result = sysDictionaryService.selectDictListTree();
    	return new ResultMessage().success(ResultEnum.SUCCESS).addData("dict", result);
	}
	
	@ApiOperation(value="保存修改字典", notes="保存修改字典方法", httpMethod="POST")
	@RequestMapping(value = "/saveorupdatedict", method = RequestMethod.POST)
    public @ResponseBody ResultMessage saveOrUpdateDict(SysDictionary sysDictionary) throws Exception {
    	return sysDictionaryService.saveOrUpdateDict(sysDictionary, "");
	}
	
	@ApiOperation(value="添加编辑字典页面", notes="跳转到字典添加页面", httpMethod="POST")
	@RequestMapping(value = "/getdict", method = RequestMethod.POST)
	public @ResponseBody ResultMessage getDict(String sdId) {
		SysDictionary sysDictionary = sysDictionaryService.selectDictByDictId(sdId);
		return new ResultMessage().success(ResultEnum.SUCCESS).addData("dict", sysDictionary);
	}
	
	@ApiOperation(value="删除字典", notes="删除字典方法", httpMethod="POST")
	@RequestMapping(value = "/removedict", method = RequestMethod.POST)
    public @ResponseBody ResultMessage removeDict(HttpServletRequest request, String sdId) throws Exception {
    	return sysDictionaryService.deleteDictByDictId(sdId);
	}
}
