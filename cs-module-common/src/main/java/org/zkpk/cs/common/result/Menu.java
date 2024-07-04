package org.zkpk.cs.common.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Menu implements Serializable {
	
	private static final long serialVersionUID = 4596018690186736206L;
	
	private String id;
	
	private String pid;

	private String name;
	
	private String icon;

	private String path;

	private String redirect;

	private Map<String, Object> meta;

	private String firstComponent;
	
	private String component;

	private List<Menu> children = new ArrayList<Menu>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public Map<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}

	public String getFirstComponent() {
		return firstComponent;
	}

	public void setFirstComponent(String firstComponent) {
		this.firstComponent = firstComponent;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}
	
	public void addChildren(Menu menu) {
        this.children.add(menu);
    }
}
