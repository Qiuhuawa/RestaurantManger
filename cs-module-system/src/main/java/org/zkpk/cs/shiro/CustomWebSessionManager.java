package org.zkpk.cs.shiro;


import javax.servlet.ServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

public class CustomWebSessionManager extends DefaultWebSessionManager {
	///监听初始化事件，根据路径上的请求参数，建立第一次请求时sessionId和请求系统之间的对应关系。以便在校验阶段根据sessionId进行路径拼接
	@Override
    protected void onStart(Session session, SessionContext context) {
        super.onStart(session, context);
        ServletRequest request = WebUtils.getRequest(context);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
    }

}
