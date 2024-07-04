package org.zkpk.cs.common.result;

import java.io.Serializable;

/**
 * 
 * @ClassName: Tree  
 * @Description: 树形结构实体类
 * @author huchao  
 * @date 2017年5月22日  
 *
 */
public class Tree extends TreeNode implements Serializable {

	private static final long serialVersionUID = 8684510619892556607L;
	private String title;
	private String text;
	private String state = "open";// open, closed
	private boolean checked = false;
	private Object attributes;
	private String icon;
	private boolean loading = false;
	private Boolean isParent;
	private Boolean open;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public void setState(Integer opened) {
		this.state = (null != opened && opened == 1) ? "open" : "closed";
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public Object getAttributes() {
		return attributes;
	}
	
	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public boolean isLoading() {
		return loading;
	}

	public void setLoading(boolean loading) {
		this.loading = loading;
	}

	public Boolean getIsParent() {
		return isParent;
	}
	
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	
	public Boolean getOpen() {
		return open;
	}
	
	public void setOpen(Boolean open) {
		this.open = open;
	}
}
