package org.zkpk.cs.shiro;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkpk.cs.dto.SysResourceDto;
import org.zkpk.cs.entity.SysResource;
import org.zkpk.cs.entity.SysUserOrganization;


/**
 * 
 * @Description: 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
 * @author HUCHAO
 * @date 2018年4月2日 上午9:34:16
 */
public class ShiroUser implements Serializable {

	/**
	 * 序列化UID
	 */
	private static final long serialVersionUID = 8596248903611242065L;
	
	/**
	 * 用户ID
	 */
	private String id;
	
	/**
	 * 用户登录名
	 */
	private String loginName;
	
	/**
	 * 用户真实姓名
	 */
	private String realName;
	
	/**
	 * 上次修改密码时间
	 */
	private Date lastChgPwdTime;
	
	/**
	 * 组织关联信息
	 */
	private SysUserOrganization sysUserOrganization;
	
	/**
	 * 用户菜单
	 */
	private List<SysResourceDto> menuList;
	
	/**
	 * 用户菜单可用权限
	 */
	private Map<String, SysResource> hasAvailableMenuResources = new HashMap<String, SysResource>();
	
	/**
	 * 用户菜单可授权权限
	 */
	private Map<String, SysResource> hasAuthorizationMenuResources = new HashMap<String, SysResource>();
	
	/**
	 * 加入更多的自定义参数
	 */
	private Map<String, Object> attribute = new HashMap<String, Object>();
	
	public ShiroUser(String loginName) {
		this.loginName = loginName;
	}
	
	public ShiroUser(String id, String loginName) {
		this.id = id;
		this.loginName = loginName;
	}
	
	public ShiroUser(String id, String loginName, String userName) {
		this.id = id;
		this.loginName = loginName;
		this.realName = userName;
	}
	
	public ShiroUser(String id, String loginName, String userName, SysUserOrganization sysUserOrganization) {
		this.id = id;
		this.loginName = loginName;
		this.realName = userName;
		this.sysUserOrganization = sysUserOrganization;
	}
	
	public ShiroUser(String id, String loginName, String userName, Date lastChgPwdTime, SysUserOrganization sysUserOrganization) {
		this.id = id;
		this.loginName = loginName;
		this.realName = userName;
		this.lastChgPwdTime = lastChgPwdTime;
		this.sysUserOrganization = sysUserOrganization;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLoginName() {
		return loginName;
	}
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public Date getLastChgPwdTime() {
		return lastChgPwdTime;
	}

	public void setLastChgPwdTime(Date lastChgPwdTime) {
		this.lastChgPwdTime = lastChgPwdTime;
	}

	public SysUserOrganization getSysUserOrganization() {
		return sysUserOrganization;
	}

	public void setSysUserOrganization(SysUserOrganization sysUserOrganization) {
		this.sysUserOrganization = sysUserOrganization;
	}

	public Map<String, Object> getAttribute() {
		return attribute;
	}
	
	public void setAttribute(Map<String, Object> attribute) {
		this.attribute = attribute;
	}

	public List<SysResourceDto> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<SysResourceDto> menuList) {
		this.menuList = menuList;
	}

	public Map<String, SysResource> getHasAvailableMenuResources() {
		return hasAvailableMenuResources;
	}

	public void setHasAvailableMenuResources(Map<String, SysResource> hasAvailableMenuResources) {
		this.hasAvailableMenuResources = hasAvailableMenuResources;
	}

	public Map<String, SysResource> getHasAuthorizationMenuResources() {
		return hasAuthorizationMenuResources;
	}

	public void setHasAuthorizationMenuResources(Map<String, SysResource> hasAuthorizationMenuResources) {
		this.hasAuthorizationMenuResources = hasAuthorizationMenuResources;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return loginName;
	}

}
