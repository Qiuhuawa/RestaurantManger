package org.zkpk.cs.support.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.util.WebUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.zkpk.cs.entity.SysLogWithBLOBs;
import org.zkpk.cs.service.SysLogService;
import org.zkpk.cs.shiro.ShiroUser;
import org.zkpk.cs.support.AddressUtils;
import org.zkpk.cs.support.annotation.Log;
import org.zkpk.cs.common.utils.IpUtil;
import org.zkpk.cs.common.utils.StringUtil;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * AOP 记录用户操作日志
 */
@Aspect
@Component
public class LogAspect {

    private Logger log = LoggerFactory.getLogger(LogAspect.class);

    // 是否使用日志
 	@Value("${cs.shiro.uselogaspect}")
 	private String useLogAspect = "0";

    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(org.zkpk.cs.support.annotation.Log)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws JsonProcessingException {
        Object result = null;
        long beginTime = System.currentTimeMillis();
        try {
            // 执行方法
            result = point.proceed();
        } catch (Throwable e) {
            log.error("获取操作信息出错了" + e.getMessage());
        }
        if ("1".equals(useLogAspect)) {
        	// 执行时长(毫秒)
        	long operationTime = System.currentTimeMillis() - beginTime;
        	// 获取request
        	HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            // 保存日志
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            String operationIp = IpUtil.getIpFromRequest(WebUtils.toHttp(request));//获取IP地址
            SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
            MethodSignature signature = (MethodSignature) point.getSignature();
	        Method method = signature.getMethod();
	        Log logAnnotation = method.getAnnotation(Log.class);
	        if (logAnnotation != null) {
	            // 注解上的描述
	        	sysLog.setOperation(logAnnotation.value());
	        }
	        // 请求的类名
	        String className = point.getTarget().getClass().getName();
	        // 请求的方法名
	        String methodName = signature.getName();
	        sysLog.setMethod(className + "." + methodName + "()");
	        // 请求的方法参数值
	        Object[] args = point.getArgs();
	        // 请求的方法参数名称
	        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
	        String[] paramNames = u.getParameterNames(method);
	        if (args != null && paramNames != null) {
	            StringBuilder params = new StringBuilder();
	            params = handleParams(params, args, Arrays.asList(paramNames));
	            sysLog.setParams(params.toString());
	        }
            String slId = StringUtil.getDateUUId();
            sysLog.setSlId(slId);
            sysLog.setUserId(shiroUser.getId());
            sysLog.setLoginName(shiroUser.getLoginName());
            sysLog.setRealName(shiroUser.getRealName());
            sysLog.setOperationIp(operationIp);
            sysLog.setOperationTime(operationTime);
            sysLog.setOperationLocation(AddressUtils.getCityInfo(operationIp));
            sysLog.setCreateBy(shiroUser.getId());
            sysLog.setCreateTime(new Date());
            sysLogService.saveSysLogWithBLOBs(sysLog);
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
    private StringBuilder handleParams(StringBuilder params, Object[] args, List paramNames) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof Map) {
				Set set = ((Map) args[i]).keySet();
				List list = new ArrayList();
				List paramList = new ArrayList<>();
				for (Object key : set) {
					list.add(((Map) args[i]).get(key));
					paramList.add(key);
				}
				return handleParams(params, list.toArray(), paramList);
			} else {
				if (args[i] instanceof Serializable) {
					Class<?> aClass = args[i].getClass();
					try {
						aClass.getDeclaredMethod("toString", new Class[] { null });
						// 如果不抛出NoSuchMethodException 异常则存在 toString 方法 ，安全的writeValueAsString ，否则 走 Object的 toString方法
						params.append("  ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i]));
					} catch (NoSuchMethodException e) {
						params.append("  ").append(paramNames.get(i)).append(": ")
								.append(objectMapper.writeValueAsString(args[i].toString()));
					}
				} else if (args[i] instanceof MultipartFile) {
					MultipartFile file = (MultipartFile) args[i];
					params.append("  ").append(paramNames.get(i)).append(": ").append(file.getName());
				} else {
					params.append("  ").append(paramNames.get(i)).append(": ").append(args[i]);
				}
			}
		}
        return params;
    }
    
}
