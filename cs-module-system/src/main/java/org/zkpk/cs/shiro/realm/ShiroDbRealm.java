package org.zkpk.cs.shiro.realm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkpk.cs.shiro.ShiroUser;
import org.zkpk.cs.shiro.utils.ReByteSource;
import org.zkpk.cs.common.utils.DateUtil;
import org.zkpk.cs.common.utils.EncodeUtil;
import org.zkpk.cs.dto.SysResourceDto;
import org.zkpk.cs.dto.SysRoleResourceDto;
import org.zkpk.cs.dto.SysUserDto;
import org.zkpk.cs.dto.SysUserRoleDto;
import org.zkpk.cs.entity.SysResource;
import org.zkpk.cs.entity.SysRole;
import org.zkpk.cs.service.SysResourceService;
import org.zkpk.cs.service.SysRoleService;
import org.zkpk.cs.service.SysUserService;


/**
 * 
 * @Description: 自定义realm
 * @author HUCHAO
 * @date 2018年4月2日 上午11:26:19
 */
public class ShiroDbRealm extends AuthorizingRealm {
	
    private static final Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);
    
    @Autowired
    private SysUserService sysUserService;
    
    @Autowired
    private SysRoleService sysRoleService;
    
    @Autowired
    private SysResourceService sysResourceService;
    
    // 是否启用超级管理员
 	protected boolean activeRoot = false;
 	
    public boolean isActiveRoot() {
		return activeRoot;
	}

	public void setActiveRoot(boolean activeRoot) {
		this.activeRoot = activeRoot;
	}

	public ShiroDbRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
        super(cacheManager, matcher);
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用。
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Collection<?> collection = principals.fromRealm(getName());
		if (collection == null || collection.isEmpty()) {
			return null;
		}
		ShiroUser shiroUser = (ShiroUser) collection.iterator().next();
		return newAuthorizationInfo(shiroUser);
	}
	
	/**
	 * 新的AuthorizationInfo
	 * @param shiroUser
	 * @return
	 */
	private SimpleAuthorizationInfo newAuthorizationInfo(ShiroUser shiroUser) {
		Collection<String> hasPermissions = null;
		Collection<String> hasRoles = null;
		
		// 是否启用超级管理员 && id==1为超级管理员，构造所有权限 
		if (activeRoot && "1".equals(shiroUser.getId())) {
			hasRoles = new HashSet<String>();
			List<SysRole> roles = sysRoleService.selectRoleList();
			for (SysRole role : roles) {
				hasRoles.add(role.getRoleName());
			}
			hasPermissions = new HashSet<String>();
			hasPermissions.add("*");
			if (logger.isInfoEnabled()) {
				logger.info("使用了" + shiroUser.getLoginName() + "的超级管理员权限:" + "。At " + new Date());
				logger.info(shiroUser.getLoginName() + "拥有的角色:" + hasRoles);
				logger.info(shiroUser.getLoginName() + "拥有的权限:" + hasPermissions);
			}
		} else {
			List<SysUserRoleDto> sysUserRoleList = sysUserService.selectUserRoleListByUserId(shiroUser.getId());
			Collection<SysRole> roles = getUserRoles(sysUserRoleList);
			hasRoles = makeRoles(roles, shiroUser);
			hasPermissions = makePermissions(sysUserRoleList, shiroUser);
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(hasRoles);
		info.addStringPermissions(hasPermissions);
		return info;
	}

	/**
	 * shiro登录认证(原理：用户提交 用户名和密码  --- shiro 封装令牌 ---- realm 通过用户名将密码查询返回 ---- shiro 自动去比较查询出密码和用户输入密码是否一致---- 进行登陆控制 )
	 * 对认证进行缓存处理
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		logger.info("shiro开始登录认证");
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String loginName = token.getUsername(); //登录账号
		if(logger.isTraceEnabled()){
			logger.info("开始认证 "+ loginName);
		}
		try {
			if(StringUtils.isBlank(loginName)){
				throw new AccountException("can not handle this login");
			}
			//查询用户信息
			SysUserDto userDto = sysUserService.selectUserByLoginName(loginName);
			//校验用户
			checkExtUser(userDto, loginName);
			//构造shiro用户
			ShiroUser shiroUser = new ShiroUser(userDto.getSuId(), userDto.getLoginName(), userDto.getRealName(), userDto.getLastChgPwdTime(), userDto.getSysUserOrganization());
			//获取当前用户菜单
			List<SysResourceDto> menuResourceList = sysResourceService.selectMenuResourceDtoByUserId(userDto.getSuId());//菜单资源
			shiroUser.setMenuList(menuResourceList);//可用菜单
			// 这里可以缓存认证
			return new SimpleAuthenticationInfo(shiroUser, userDto.getPassword().toCharArray(), ReByteSource.ofbyte(EncodeUtil.decodeHex(userDto.getSalt())), getName());
		} catch (Exception e) {
			throw translateAuthenticationException(e);
		} 
	}
	
	
	/**
	 * 检查用户
	 * @param userDto
	 * @param loginName
	 */
	private void checkExtUser(SysUserDto userDto, String loginName) {
		if (null == userDto) {
			throw new UnknownAccountException(loginName + " can not find " + loginName + " from system");
		}
		// 帐号有效期过期
		if (userDto.getClosingDate() != null && DateUtil.compareDateTime(new Date(), userDto.getClosingDate())) {
			throw new ExpiredCredentialsException("the account for username " + loginName + "  was expired.");
		}
		// 账号状态(锁定、禁用)
		if ("USER_STATUS_LOCK".equals(userDto.getUserStatus()) || "USER_STATUS_DISABLED".equals(userDto.getUserStatus())) {
			throw new DisabledAccountException("the account is not available now");
		}
		// 账号登录错误次数
		if (userDto.getLoginErrorCount() > 5) {
			throw new LockedAccountException();
		}
	}
	
	/**
	 * 异常转换
	 * @param exception
	 * @return
	 */
	private AuthenticationException translateAuthenticationException(Exception exception) {
		if(exception instanceof IncorrectCredentialsException){
			return (IncorrectCredentialsException)exception;
		}
		if(exception instanceof ExcessiveAttemptsException){
			return (ExcessiveAttemptsException)exception;
		}
		if(exception instanceof LockedAccountException){
			return (LockedAccountException)exception;
		}
		if(exception instanceof AuthenticationException) {
			return (AuthenticationException)exception;
		}
		if(exception instanceof DisabledAccountException){
			return (DisabledAccountException)exception;
		}
		if(exception instanceof UnknownAccountException){
			return (UnknownAccountException)exception;
		}
		if(exception instanceof ExpiredCredentialsException){
			return (ExpiredCredentialsException)exception;
		}
		if(exception instanceof UnauthorizedException){
			return (AuthenticationException)exception;
		}
		return new AuthenticationException(exception);
	}
	
	/**
	 * 
	 * @Description: 封装role
	 * @author HUCHAO
	 * @date 2018年4月2日 下午4:18:50
	 * @param userRoles
	 * @return
	 */
	private Collection<SysRole> getUserRoles(List<SysUserRoleDto> sysUserRoleList) {
		Set<SysRole> roles = new HashSet<SysRole>();
		for (SysUserRoleDto userRole : sysUserRoleList) {
			roles.add(userRole.getRole());
		}
		return roles;
	}
	
    /**
     * 
     * @Title: makeRoles  
     * @Description: 组装角色权限
     * @param @param roles
     * @param @param shiroUser
     * @return Collection<String>    返回类型  
     * @throws
     */
	private Collection<String> makeRoles(Collection<SysRole> roles, ShiroUser shiroUser) {
		Collection<String> hasRoles = new HashSet<String>();
		for (SysRole role : roles) {
			hasRoles.add(role.getRoleName());
		}
		if (logger.isInfoEnabled()) {
			logger.info(shiroUser.getLoginName() + "拥有的角色:" + hasRoles);
		}
		return hasRoles;
	}
	
	/**
	 * 
	 * @Description: 封装权限
	 * @author HUCHAO
	 * @date 2018年4月2日 下午4:29:43
	 * @param sysUserRoleList
	 * @param shiroUser
	 * @return
	 */
	private Collection<String> makePermissions(List<SysUserRoleDto> sysUserRoleList, ShiroUser shiroUser) {
		Collection<String> stringPermissions = new ArrayList<String>();
		for (SysUserRoleDto sysUserRole : sysUserRoleList) {
			List<SysRoleResourceDto> roleResourceList = sysRoleService.selectRoleResourceListByRoleId(sysUserRole.getRole().getSrId());
			for (SysRoleResourceDto roleResource : roleResourceList) {
				StringBuilder builder = new StringBuilder();//返回权限串
				SysResource resource = roleResource.getResource();//权限资源
				if ("RESOURCE_TYPE_MENU".equals(resource.getResourceType())) {
					String menuCode = resource.getResourceCode();//菜单编码
					builder.append(menuCode + ":SHOW");
				} else {
					SysResource menuResource = sysResourceService.selectMenuResourceByRightParentTrId(resource.getParentSrId());//菜单资源
					String menuCode = menuResource.getResourceCode();//菜单编码
					String operateCode = resource.getResourceCode();//权限编码
					builder.append(menuCode + ":" + operateCode);
				}
				stringPermissions.add(builder.toString());//拥有权限
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info(shiroUser.getLoginName() + "拥有的权限:" + stringPermissions);
		}
		return stringPermissions;
	}
	
	/**
	 * 覆盖父类方法，设置AuthorizationCacheKey为ShiroUser的loginName，这样才能顺利删除shiro中的缓存。
	 * 因为shiro默认的AuthorizationCacheKey为PrincipalCollection的对象。
	 */
	@Override
	protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser)principals.getPrimaryPrincipal();
		return shiroUser.getLoginName();
	}

	/**
	 * 
	 * @Description: 清空用户关联权限认证，待下次使用时重新加载
	 * @author HUCHAO
	 * @date 2018年4月3日 下午2:16:06
	 * @param loginName
	 */
	public void clearCachedAuthorizationInfo(String loginName) {
		ShiroUser shiroUser = new ShiroUser(loginName);
		SimplePrincipalCollection principals = new SimplePrincipalCollection(shiroUser, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 
	 * @Description: 清除所有用户授权信息缓存.
	 * @author HUCHAO
	 * @date 2018年4月3日 下午2:16:22
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
	
	/**
	 * 退出系统
	 */
    @Override
    public void onLogout(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        removeUserCache(shiroUser);
    }

    /**
     * 
     * @Description: 清除用户缓存
     * @author HUCHAO
     * @date 2018年4月3日 下午2:16:43
     * @param shiroUser
     */
    public void removeUserCache(ShiroUser shiroUser){
        removeUserCache(shiroUser.getLoginName());
    }

    /**
     * 
     * @Description: 清除用户缓存
     * @author HUCHAO
     * @date 2018年4月3日 下午2:16:56
     * @param loginName
     */
    public void removeUserCache(String loginName){
        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        principals.add(loginName, super.getName());
        super.clearCachedAuthenticationInfo(principals);
        clearCachedAuthorizationInfo(loginName);
    }


}
