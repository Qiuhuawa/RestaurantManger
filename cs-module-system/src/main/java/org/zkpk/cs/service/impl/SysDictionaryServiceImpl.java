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
import org.zkpk.cs.common.result.ResultEnum;
import org.zkpk.cs.common.result.ResultMessage;
import org.zkpk.cs.common.result.Tree;
import org.zkpk.cs.common.utils.StringUtil;
import org.zkpk.cs.common.utils.TreeUtil;
import org.zkpk.cs.entity.SysDictionary;
import org.zkpk.cs.mapper.SysDictionaryMapper;
import org.zkpk.cs.service.SysDictionaryService;

@Service
public class SysDictionaryServiceImpl implements SysDictionaryService{

	@Autowired
	private SysDictionaryMapper sysDictionaryMapper;
	
	@Override
	public List<SysDictionary> selectSysDictionaryList(HttpServletRequest request, String string) {
		String dictCode = request.getParameter("dictCode");
		String dictName = request.getParameter("dictName");
		//查询参数信息
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("dictCode", dictCode);
		parameterMap.put("dictName", dictName);
		List<SysDictionary> dictlist = sysDictionaryMapper.selectSysDictListByParameter(parameterMap);
		return dictlist;
	}

	@Override
	public List<Tree> selectDictListTree() {
		List<Tree> resultlist = new ArrayList<Tree>();
		//查询参数信息
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		List<SysDictionary> dictlist = sysDictionaryMapper.selectSysDictListByParameter(parameterMap);
		for (SysDictionary sysDictionary : dictlist) {
			Tree tree = new Tree();
			tree.setId(sysDictionary.getSdId());  //ID
			tree.setParentId(sysDictionary.getParentSdId()); //父ID
			tree.setTitle(sysDictionary.getDictName()); //名称
			tree.setText(sysDictionary.getDictName()); //名称
			tree.setAttributes(sysDictionary);//自定义属性
			resultlist.add(tree);
		}
		resultlist = TreeUtil.buildTreeBy2Loop(resultlist, "");
		return resultlist;
	}

	@Override
	public ResultMessage saveOrUpdateDict(SysDictionary sysDictionary, String string) {
		Date dt = new Date();//系统当前时间
		//查询字典编码是否存在
		String dictCode = sysDictionary.getDictCode().toUpperCase();
		//修改
		if (sysDictionary.getSdId() != null && !"".equals(sysDictionary.getSdId())) {
			//查询
			SysDictionary result = sysDictionaryMapper.selectByPrimaryKey(sysDictionary.getSdId());
			if (!result.getDictCode().equals(dictCode)) { //编码不一致去查询新的编码是否存在
				Map<String, Object> parameterMap = new HashMap<String, Object>();
				parameterMap.put("dictCode", dictCode);//字典编码
				List<SysDictionary> sysdictionarylist = sysDictionaryMapper.selectSysDictListByParameter(parameterMap);
				if (sysdictionarylist != null && sysdictionarylist.size() > 0) {
					throw new LabException(ResultEnum.CANNOT_ADD_CODE_EXIST);//编码存在
				}
				sysDictionary.setDictCode(dictCode);//字典编码
			}
			sysDictionary.setUpdateBy("");//变更者
			sysDictionary.setUpdateTime(dt);//变更时间
			//修改字典
			int resultFlag = sysDictionaryMapper.updateByPrimaryKeySelective(sysDictionary);
			if (resultFlag == 1) {//修改成功
				return new ResultMessage().success(ResultEnum.EDIT_SUCCESS);
			} else {
				throw new LabException(ResultEnum.EDIT_FAILED);
			}
		} else {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("dictCode", dictCode);//字典编码
			List<SysDictionary> sysdictionarylist = sysDictionaryMapper.selectSysDictListByParameter(parameterMap);
			if (sysdictionarylist != null && sysdictionarylist.size() > 0) {
				throw new LabException(ResultEnum.CANNOT_ADD_CODE_EXIST);//编码存在
			}
			if (sysDictionary.getParentSdId() != null && !"".equals(sysDictionary.getParentSdId())) {
				SysDictionary parentDict = sysDictionaryMapper.selectByPrimaryKey(sysDictionary.getParentSdId());
				sysDictionary.setTargetCode(parentDict.getDictCode());
			}
			
			String sdId = StringUtil.getDateUUId();
			sysDictionary.setSdId(sdId);//主键
			sysDictionary.setDictCode(dictCode);//字典编码
			sysDictionary.setCreateBy("");//作成者
			sysDictionary.setCreateTime(dt);//作成时间
			//添加字典
			int resultFlag = sysDictionaryMapper.insertSelective(sysDictionary);
			if (resultFlag == 1) {//添加成功
				return new ResultMessage().success(ResultEnum.ADD_SUCCESS);
			} else {
				throw new LabException(ResultEnum.ADD_FAILED);
			}
		}
	}
	
	@Override
	public SysDictionary selectDictByDictId(String sdId) {
		SysDictionary sysDictionary = new SysDictionary();
		if (StringUtil.isNotNull(sdId)) {
			sysDictionary = sysDictionaryMapper.selectByPrimaryKey(sdId);
		}
		return sysDictionary;
	}

	@Override
	public ResultMessage deleteDictByDictId(String sdId) {
		//查询参数信息
				Map<String ,Object> parameterMap = new HashMap<String ,Object>();
				parameterMap.put("parentSdId", sdId);//父ID
				List<SysDictionary> dictlist = sysDictionaryMapper.selectSysDictListByParameter(parameterMap);
				if (dictlist != null && dictlist.size() > 0) {
					//该节点存在子集，不能删除
					throw new LabException(ResultEnum.CANNOT_DELETED);
				} else {
					//删除字典
					int resultFlag = sysDictionaryMapper.deleteByPrimaryKey(sdId);
					if (resultFlag == 1) {//删除成功
						return new ResultMessage().success(ResultEnum.DELETE_SUCCESS);
					} else {
						throw new LabException(ResultEnum.DELETE_FAILED);
					}
				}
	}

	@Override
	public List<SysDictionary> selectDictDataByTargetCode(String targetCode) {
		// TODO Auto-generated method stub
		List<SysDictionary> dictlist = new ArrayList<SysDictionary>();
		if(targetCode != null && !"".equals(targetCode)){
			Map<String ,Object> parameterMap = new HashMap<String ,Object>();//参数map
			parameterMap.put("targetCode", targetCode.toUpperCase());
			parameterMap.put("isAvailable", "1");//可用
			dictlist = sysDictionaryMapper.selectSysDictListByParameter(parameterMap);//查询字典集合
		}
		return dictlist;
	}

	@Override
	public List<SysDictionary> selectDictDataByTargetCodeWithOutRemoveCode(String targetCode, String removeCode) {
		// TODO Auto-generated method stub
		List<SysDictionary> dictlist = new ArrayList<SysDictionary>();
		if(targetCode != null && !"".equals(targetCode)){
			Map<String ,Object> parameterMap = new HashMap<String, Object>(); //参数map
			parameterMap.put("targetCode", targetCode.toUpperCase());
			parameterMap.put("isAvailable", "1");//可用
			if(removeCode != null && !"".equals(removeCode)){
				String[] removeCodes = removeCode.toUpperCase().split(",");
				parameterMap.put("removeCodes", removeCodes);
			}
			dictlist = sysDictionaryMapper.selectSysDictListByParameter(parameterMap);//查询字典集合
		}
		return dictlist;
	}

	@Override
	public Map<String, Object> selectMapDictDataByTargetCode(String targetCode) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		List<SysDictionary> dictlist = new ArrayList<SysDictionary>();
		if(targetCode != null && !"".equals(targetCode)){
			Map<String ,Object> parameterMap = new HashMap<String ,Object>();//参数map
			parameterMap.put("targetCode", targetCode.toUpperCase());
			parameterMap.put("isAvailable", "1");//可用
			dictlist = sysDictionaryMapper.selectSysDictListByParameter(parameterMap);//查询字典集合
	        if (dictlist != null && dictlist.size() > 0) {
	            for (SysDictionary dictData : dictlist) {
	            	resultMap.put(dictData.getDictCode(), dictData.getDictName());
	            }
	        }
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> selectMapDictDataByTargetCodeWithOutRemoveCode(String targetCode, String removeCode) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		List<SysDictionary> dictlist = new ArrayList<SysDictionary>();
		if(targetCode != null && !"".equals(targetCode)){
			Map<String ,Object> parameterMap = new HashMap<String ,Object>();//参数map
			parameterMap.put("targetCode", targetCode.toUpperCase());
			parameterMap.put("isAvailable", "1");//可用
			if(removeCode != null && !"".equals(removeCode)){
				String[] removeCodes = removeCode.toUpperCase().split(",");
				parameterMap.put("removeCodes", removeCodes);
			}
			dictlist = sysDictionaryMapper.selectSysDictListByParameter(parameterMap);//查询字典集合
	        if (dictlist != null && dictlist.size() > 0) {
	            for (SysDictionary dictData : dictlist) {
	            	resultMap.put(dictData.getDictCode(), dictData.getDictName());
	            }
	        }
		}
		return resultMap;
	}
}
