package org.zkpk.cs.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.zkpk.cs.common.result.TreeNode;

/**
 * 
 * @Description: 树结构相关工具类
 * @author HUCHAO
 * @date 2018-10-19 14:30:48
 */
public class TreeUtil {

	/*
	 * *
	 * @Description 用双重循环建树
	 * @Param [treeNodes树节点列表, root根标志]
	 * @Return java.util.List<T>
	 */
	public static <T extends TreeNode> List<T> buildTreeBy2Loop(List<T> treeNodes, Object root) {
		List<T> trees = new ArrayList<>();
		for (T node : treeNodes) {
			if (root.equals(node.getParentId())) {
				trees.add(node);
			}
			for (T treeNode : treeNodes) {
				if (node.getId().equals(treeNode.getParentId())) {
					if (node.getChildren() == null) {
						node.setChildren(new ArrayList<>());
					}
					node.addChilren(treeNode);
				}
			}
		}
		return trees;
	}

}
