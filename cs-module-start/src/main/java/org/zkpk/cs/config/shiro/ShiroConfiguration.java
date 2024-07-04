package org.zkpk.cs.config.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.zkpk.cs.shiro.filter.KickoutSessionControlFilter;
import org.zkpk.cs.shiro.filter.ShiroAjaxSessionFilter;
import org.zkpk.cs.shiro.listener.ShiroSessionListener;
import org.zkpk.cs.shiro.matcher.RetryLimitCredentialsMatcher;
import org.zkpk.cs.shiro.realm.ShiroDbRealm;
import org.zkpk.cs.shiro.utils.PasswordHash;

@Configuration
public class ShiroConfiguration {
	
    @Bean
    public EhCacheManager shiroSpringCacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:xmlconfig/ehcache-system.xml");
        return cacheManager;
    }
	
	@Bean
	public RetryLimitCredentialsMatcher credentialsMatcher() {
		RetryLimitCredentialsMatcher retryLimitCredentialsMatcher = new RetryLimitCredentialsMatcher(shiroSpringCacheManager());
		retryLimitCredentialsMatcher.setRetryLimitCacheName("halfHour");
		retryLimitCredentialsMatcher.setPasswordHash(passwordHash());
		return retryLimitCredentialsMatcher;
	}
    
    @Bean
    public PasswordHash passwordHash() {
    	PasswordHash passwordHash = new PasswordHash();
    	passwordHash.setAlgorithmName("SHA-256");//散列算法:这里使用SHA-256算法;
    	passwordHash.setHashIterations(2);//散列的次数，比如散列两次，相当于 SHA-256(SHA-256(""));
    	passwordHash.setStoredCredentialsHexEncoded(true);
    	return passwordHash;
    }
    
    @Bean
    public ShiroDbRealm shiroRealm() {
    	ShiroDbRealm shiroDbRealm = new ShiroDbRealm(shiroSpringCacheManager(), credentialsMatcher());
    	shiroDbRealm.setAuthenticationCachingEnabled(true);
    	shiroDbRealm.setAuthenticationCacheName("authenticationCache");
    	shiroDbRealm.setAuthorizationCacheName("authorizationCache");
    	shiroDbRealm.setActiveRoot(true);
        return shiroDbRealm;
    }

    /**
     * rememberMe管理器, cipherKey生成见{@code Base64Test.java}
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie) {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCipherKey(Base64.decode("5aaC5qKm5oqA5pyvAAAAAA=="));
        manager.setCookie(rememberMeCookie);
        return manager;
    }

    /**
     * 记住密码Cookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(7 * 24 * 60 * 60);//7天
        return simpleCookie;
    }
    
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置 SecurityManager，并注入 shiroRealm
        securityManager.setRealm(shiroRealm());
        // 配置 rememberMeCookie
        //securityManager.setRememberMeManager(rememberMeManager());
        // 配置 缓存管理类 cacheManager
        securityManager.setCacheManager(shiroSpringCacheManager());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }
    
    /**
     * 
     * @Description: 登录并发控
     * @author HUCHAO
     * @date 2019-01-11 18:53:22
     * @param sessionManager
     * @return
     */
    @Bean
	public KickoutSessionControlFilter kickoutSessionControlFilter() {
		KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
		kickoutSessionControlFilter.setCacheManager(shiroSpringCacheManager());
		kickoutSessionControlFilter.setSessionManager(sessionManager());
		kickoutSessionControlFilter.setKickoutAfter(false);
		kickoutSessionControlFilter.setMaxSession(1);
		kickoutSessionControlFilter.setKickoutUrl("/?kickout=1");
		return kickoutSessionControlFilter;
	}
    
    @Bean
    public ShiroAjaxSessionFilter shiroAjaxSessionFilter() {
    	ShiroAjaxSessionFilter shiroAjaxSessionFilter = new ShiroAjaxSessionFilter();
    	return shiroAjaxSessionFilter;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        //默认的登陆访问url
        shiroFilter.setLoginUrl("/");
        //登陆成功后跳转的url
        shiroFilter.setSuccessUrl("/index");
        //没有权限跳转的url
        shiroFilter.setUnauthorizedUrl("/error/403");
        //覆盖默认的user拦截器
        Map<String, Filter> filters = new HashMap<String, Filter>();
		// 登录并发拦截
		filters.put("kickout", kickoutSessionControlFilter());
		filters.put("user", shiroAjaxSessionFilter());
        shiroFilter.setFilters(filters);
        /**
         * 配置shiro拦截器链  anon-不需要认证 authc-需要认证 user-验证通过或RememberMe登录的都可以
         * 当应用开启了rememberMe时,用户下次访问时可以是一个user,但不会是authc,因为authc是需要重新认证的
         * 顺序从上到下,优先级依次降低
         */
        Map<String, String> filterChain = new LinkedHashMap<>();
        filterChain.put("/login", "anon");
        filterChain.put("/assets/**", "anon");
        filterChain.put("/upload/**", "anon");
        filterChain.put("/**", "kickout,authc");
        shiroFilter.setFilterChainDefinitionMap(filterChain);
        return shiroFilter;
    }
    
    /**
	 * 会话DAO
	 * @return
	 */
	@Bean
	public EnterpriseCacheSessionDAO sessionDAO() {
		System.out.println("ShiroConfiguration.mySessionDao()");
		EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
		sessionDAO.setActiveSessionsCacheName("activeSessionCache");
		sessionDAO.setCacheManager(shiroSpringCacheManager());
		return sessionDAO;
	}

    /**
     * session 管理对象
     * @return DefaultWebSessionManager
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
    	System.out.println("ShiroConfiguration.sessionManager()");
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<>();
        listeners.add(new ShiroSessionListener());
        // 设置session超时时间，单位为毫秒
        sessionManager.setGlobalSessionTimeout(1800000);
        sessionManager.setSessionListeners(listeners);
		sessionManager.setGlobalSessionTimeout(1800000);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.getSessionIdCookie().setName("csid");
        sessionManager.getSessionIdCookie().setMaxAge(-1);
        sessionManager.getSessionIdCookie().setHttpOnly(true);
        return sessionManager;
    }

    /**
     * 在方法中 注入 securityManager,进行代理控制
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager securityManager) {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        bean.setArguments(securityManager);
        return bean;
    }
    
    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
