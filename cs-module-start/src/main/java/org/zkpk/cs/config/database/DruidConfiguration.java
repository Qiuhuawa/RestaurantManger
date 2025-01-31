package org.zkpk.cs.config.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
public class DruidConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DruidConfiguration.class);

    @Bean
    public ServletRegistrationBean<StatViewServlet> druidServlet() {
    	logger.info("init Druid Servlet Configuration");
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<StatViewServlet>(new StatViewServlet(), "/druid/*");
        // IP白名单
//      servletRegistrationBean.addInitParameter("allow", "192.168.2.25,116.196.81.106,127.0.0.1");
        // IP黑名单(共同存在时，deny优先于allow)
//      servletRegistrationBean.addInitParameter("deny", "");
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "admin");
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }
    
    @Bean
    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<WebStatFilter>(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}
