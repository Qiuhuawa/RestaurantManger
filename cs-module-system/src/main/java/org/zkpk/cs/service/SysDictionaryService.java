package org.zkpk.cs.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkpk.cs.common.result.ResultMessage;
import org.zkpk.cs.common.result.Tree;
import org.zkpk.cs.entity.SysDictionary;

public interface SysDictionaryService {
	
	List<SysDictionary> selectSysDictionaryList(HttpServletRequest request, String string);

	List<Tree> selectDictListTree();

	ResultMessage saveOrUpdateDict(SysDictionary sysDictionary, String string);

	SysDictionary selectDictByDictId(String sdId);

	ResultMessage deleteDictByDictId(String sdId);

	List<SysDictionary> selectDictDataByTargetCode(String targetCode);

	List<SysDictionary> selectDictDataByTargetCodeWithOutRemoveCode(String targetCode, String removeCode);

	Map<String, Object> selectMapDictDataByTargetCode(String targetCode);

	Map<String, Object> selectMapDictDataByTargetCodeWithOutRemoveCode(String targetCode, String removeCode);
}
