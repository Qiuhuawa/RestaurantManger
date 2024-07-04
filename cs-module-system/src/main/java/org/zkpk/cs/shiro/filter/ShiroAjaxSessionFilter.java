package org.zkpk.cs.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;

public class ShiroAjaxSessionFilter extends UserFilter {
	
	/**
	 * 在web开发中，通常会有session超时处理，对于普通的http请求比较容易处理，而对于ajax异步请求，可能就需要特殊处理了
	 * 
	 * 自定义一个拦截器，在timeout的时候丢出
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = WebUtils.toHttp(request);
		String xmlHttpRequest = req.getHeader("X-Requested-With");
		if (StringUtils.isNotBlank(xmlHttpRequest)) {
			if (xmlHttpRequest.equalsIgnoreCase("XMLHttpRequest")) {
				HttpServletResponse res = WebUtils.toHttp(response);
				// 采用res.sendError(401);
				res.setHeader("sessionstatus", "timeout");
				return false;
			}
		}
		return super.onAccessDenied(request, response);
	}
}
