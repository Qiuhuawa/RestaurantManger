package org.zkpk.cs.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * @Description: Spring的ApplicationContext的持有者,可以用静态方法的方式获取spring容器中的bean
 * @author HUCHAO
 * @date 2018-10-19 09:59:38
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return applicationContext;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        assertApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> tClass) {
        assertApplicationContext();
        return (T) applicationContext.getBean(tClass);
    }

    private static void assertApplicationContext() {
        if (null == SpringContextHolder.applicationContext) {
            throw new RuntimeException("applicationContext为空,请检查是否注入springContextHolder");
        }
    }
}
