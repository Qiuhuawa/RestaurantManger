package org.zkpk.cs.common.result;

import java.util.ArrayList;
import java.util.List;

public abstract class TreeNode {

    protected String id;
    protected String parentId;
    protected List<TreeNode> children = new ArrayList<>();

    public void addChilren(TreeNode node) {
        this.children.add(node);
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}

