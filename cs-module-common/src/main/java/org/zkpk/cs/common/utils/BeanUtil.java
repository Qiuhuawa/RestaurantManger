package org.zkpk.cs.common.utils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtil {

	//日志
    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);
	
    /**
     * 
     * @Description: 自动匹配参数赋值到实体bean中
     * @author HUCHAO
     * @date 2018-04-25 13:49:40
     * @param bean
     * @param request
     */
    public static void populate(Object bean, HttpServletRequest request){
		Class<? extends Object> clazz = bean.getClass();
		Method ms[] = clazz.getDeclaredMethods();
		String mname;
		String field;
		String fieldType;
		String value;
		for (Method m : ms) {
			mname = m.getName();
			if (!mname.startsWith("set") || ArrayUtils.isEmpty(m.getParameterTypes())) {
				continue;
			}
			try {
				field = mname.toLowerCase().charAt(3) + mname.substring(4, mname.length());
				value = request.getParameter(field);
				if (StringUtils.isEmpty(value)) {
					continue;
				}
				fieldType = m.getParameterTypes()[0].getName();
				// 以下可以确认value为String类型
				if (String.class.getName().equals(fieldType)) {
					m.invoke(bean, (String) value);
				} else if (Integer.class.getName().equals(fieldType) && NumberUtils.isDigits((String) value)) {
					m.invoke(bean, Integer.valueOf((String) value));
				} else if (Short.class.getName().equals(fieldType) && NumberUtils.isDigits((String) value)) {
					m.invoke(bean, Short.valueOf((String) value));
				} else if (Float.class.getName().equals(fieldType) && NumberUtils.isCreatable((String) value)) {
					m.invoke(bean, Float.valueOf((String) value));
				} else if (Double.class.getName().equals(fieldType) && NumberUtils.isCreatable((String) value)) {
					m.invoke(bean, Double.valueOf((String) value));
				} else if (Date.class.getName().equals(fieldType)) {
					m.invoke(bean, DateUtils.parseDate((String) value, "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"));
				} else {
					m.invoke(bean, value);
				}
			} catch (Exception e) {
				logger.error("封装对象出错了！错误信息：" + e.getMessage());
				continue;
			}
		}
    }
    
    /**
     * 
     * @Description: 根据前缀获取数据Map
     * @author HUCHAO
     * @date 2018-04-25 13:49:20
     * @param request
     * @param prefix
     * @return
     */
    public static Map<String, Object> getParametersStartingWith(HttpServletRequest request, String prefix) {  
        Enumeration<String> paramNames = request.getParameterNames();  
        Map<String, Object> params = new HashMap<String, Object>();  
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String values[] = request.getParameterValues(paramName);
				if (values != null && values.length != 0) {
					if (values.length > 1) {
						params.put(unprefixed, values);
					} else {
						params.put(unprefixed, values[0]);
					}
				}
			}
		}  
        return params;  
    }
	
}
