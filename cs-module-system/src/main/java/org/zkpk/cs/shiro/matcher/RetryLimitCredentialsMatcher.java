package org.zkpk.cs.shiro.matcher;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.zkpk.cs.shiro.utils.PasswordHash;
import org.zkpk.cs.dto.SysUserDto;
import org.zkpk.cs.entity.SysUser;
import org.zkpk.cs.mapper.SysUserMapper;

/**
 * 输错5次密码锁定账户
 * @author huchao add by huchao 2017-04-27
 */
public class RetryLimitCredentialsMatcher extends HashedCredentialsMatcher implements InitializingBean {
	
	private final static Logger logger = LoggerFactory.getLogger(RetryLimitCredentialsMatcher.class);
	
	private final static String DEFAULT_CHACHE_NAME = "retryLimitCache";
	
	private final CacheManager cacheManager;
	
	private String retryLimitCacheName;
	
	private Cache<String, AtomicInteger> passwordRetryCache;
	
	private final Object cacheMonitor = new Object();
	
	private PasswordHash passwordHash;
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	public RetryLimitCredentialsMatcher(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
		this.retryLimitCacheName = DEFAULT_CHACHE_NAME;
	}
	
	public String getRetryLimitCacheName() {
		return retryLimitCacheName;
	}
	
	public void setRetryLimitCacheName(String retryLimitCacheName) {
		this.retryLimitCacheName = retryLimitCacheName;
	}
	
	public void setPasswordHash(PasswordHash passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
		String username = (String) authcToken.getPrincipal();
		int passwdMaxRetries = 5; // 允许最大错误次数
		int remain = 0; // 剩余错误次数
		int passwdRetries = 0; // 错误次数
		if (passwdMaxRetries > 0) {
			synchronized (cacheMonitor) {
				AtomicInteger retryCount = passwordRetryCache.get(username);
				if (retryCount == null) {
					retryCount = new AtomicInteger(0);
				}
				// retry count + 1
				passwdRetries = retryCount.incrementAndGet();
				passwordRetryCache.put(username, new AtomicInteger(passwdRetries));
			}
			remain = passwdMaxRetries - passwdRetries; // 剩余次数
			if (remain < 0) {
				String errorMsg = "密码输入错误次数过多, 账号已被锁定, 请联系管理员";
				logger.warn("username: " + username + " tried to login more than" + passwdMaxRetries + "times in period");
				throw new LockedAccountException(errorMsg);
			}
		}
		boolean matches = super.doCredentialsMatch(authcToken, info);
		if (matches) {
			passwordRetryCache.remove(username);//clear retry data
		} else {
			if (remain == 0) {
				SysUserDto sysUserDto = sysUserMapper.selectUserDtoByLoginName(username);
				if (sysUserDto != null && "USER_STATUS_NORMAL".equals(sysUserDto.getUserStatus())) {
					SysUser sysUser = new SysUser();
					Date dt = new Date();
					sysUser.setSuId(sysUserDto.getSuId());//ID
					sysUser.setUserStatus("USER_STATUS_LOCK"); // 状态 锁定
					sysUser.setUpdateBy(sysUserDto.getSuId());// 作成者
					sysUser.setUpdateTime(dt);// 作成时间
					sysUserMapper.updateByPrimaryKeySelective(sysUser);// 修改当前用户
				}
				String errorMsg = "密码输入错误次数过多, 账号已被锁定, 请联系管理员";
				logger.warn("username: " + username + " tried to login more than" + passwdMaxRetries + "times in period");
				throw new LockedAccountException(errorMsg);
			} else {
				String errorMsg = "密码输入错误 {total} 次账号将被锁定, 您还能再试 {remain} 次";
				errorMsg = errorMsg.replace("{total}", String.valueOf(passwdMaxRetries)).replace("{remain}", String.valueOf(remain));
				logger.warn("username: " + username + " tried to login more than" + passwdMaxRetries + "times in period");
				throw new ExcessiveAttemptsException(errorMsg);
			}
		}
		return matches;
	}
	
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(passwordHash, "you must set passwordHash!");
		super.setHashAlgorithmName(passwordHash.getAlgorithmName());
		super.setHashIterations(passwordHash.getHashIterations());
		super.setStoredCredentialsHexEncoded(passwordHash.isStoredCredentialsHexEncoded());
		this.passwordRetryCache = cacheManager.getCache(retryLimitCacheName);
	}
}
