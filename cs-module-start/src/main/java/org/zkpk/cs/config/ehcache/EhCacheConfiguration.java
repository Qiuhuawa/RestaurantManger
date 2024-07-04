package org.zkpk.cs.config.ehcache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ehcache配置
 */
@Configuration
@EnableCaching
public class EhCacheConfiguration {
	
	/**
     * 设置为共享模式
     * @return
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }

    /**
     * EhCache的配置
     */
    @Bean
    public EhCacheCacheManager cacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean) {
    	EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
    	ehCacheCacheManager.setCacheManager(ehCacheManagerFactoryBean.getObject());
    	ehCacheCacheManager.setTransactionAware(true);
        return ehCacheCacheManager;
    }
    
}
