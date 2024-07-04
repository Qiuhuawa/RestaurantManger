package org.zkpk.cs.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zkpk.cs.common.utils.RSAUtil;
import org.zkpk.cs.controller.base.BaseController;
import org.zkpk.cs.service.SysUserService;
import org.zkpk.cs.shiro.ShiroUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "登录")
@Controller
public class LoginController extends BaseController {
	
	@Autowired
	private SysUserService sysUserService;
	
	
	//日志
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	private static final String LOGIN_PAGE = "login"; //登录页面
	private static final String INDEX_PAGE = "main/layout"; //主页面
	
	/**
	 * 
	 * @Description: 登录页面
	 * @author HUCHAO
	 * @date 2018年4月3日 上午10:53:49
	 * @param request
	 * @return
	 */
	@ApiOperation(value="首页登录页", notes="跳转到首页", httpMethod="GET")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView showlogin(HttpServletRequest request, ModelAndView mav) {
		//使用权限管理工具进行用户的退出，跳出登录，给出提示信息  
		Subject subject = SecurityUtils.getSubject(); 
	    if (subject != null && subject.isAuthenticated()) { 
	    	ShiroUser shiroUser = getShiroUser(subject);//当前用户
	    	String userId = shiroUser.getId();//当前用户ID
	        subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存  
	    	String publicKey = RSAUtil.generateBase64PublicKey();
	    	mav.addObject("publicKey", publicKey);
	    	mav.setViewName(LOGIN_PAGE);
	    } else {
	    	String publicKey = RSAUtil.generateBase64PublicKey();
	    	mav.addObject("publicKey", publicKey);
	    	mav.setViewName(LOGIN_PAGE);
	    }
		return mav;
	}
	
	/**
	 * 
	 * @Description: 登录
	 * @author HUCHAO
	 * @date 2018年4月3日 上午10:53:31
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@ApiOperation(value="登录方法", httpMethod="POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
	public String Login(@ApiParam(value = "request请求", required = true)HttpServletRequest request, 
			@ApiParam(value = "response请求", required = true)HttpServletResponse response, 
			@ApiParam(value = "重定向参数", required = true)RedirectAttributes redirectAttributes) {
		// 获取请求页面的参数
		String loginName = request.getParameter("login_name"); // 用户名
		String password = request.getParameter("login_password"); // 密码 
		String rememberMe = request.getParameter("rememberMe"); //记住我
		String msg = "";
		// 改为全部抛出异常，避免ajax csrf token被刷新
        if (StringUtils.isBlank(loginName)) {
        	msg = "账号不能为空！";
        	redirectAttributes.addFlashAttribute("message", msg);
        }
        if (StringUtils.isBlank(password)) {
        	msg = "密码不能为空！";
        	redirectAttributes.addFlashAttribute("message", msg);
        }
        
        try {
	        // 设置记住密码
	        boolean flag = "y".equals(rememberMe);
			// 查询用户是否存在
	        try {
				password = RSAUtil.decryptBase64(password);
			} catch (Exception e) {
				msg = "账号或密码不正确！";
				redirectAttributes.addFlashAttribute("message", msg);
				logger.error("登录密码解密错误. Password Decode for account " + loginName + " was incorrect.");
			}
			UsernamePasswordToken token = new UsernamePasswordToken(loginName, password.toCharArray());
			token.setRememberMe(flag);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			if (subject != null && subject.isAuthenticated()) {
				ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
				sysUserService.updateUserLoginTimeByUserId(request, shiroUser.getId()); //修改用户登录时间
				return "redirect:/index";
			} else {
				token.clear();
			}
		} catch (IncorrectCredentialsException e) {
			msg = "账号或密码不正确！";
			redirectAttributes.addFlashAttribute("message", msg);
			logger.error("登录密码错误. Password for account " + loginName + " was incorrect.");
		} catch (ExcessiveAttemptsException e) {
			msg = e.getMessage();
			redirectAttributes.addFlashAttribute("message", msg);
			logger.error("登录失败次数过多！");
		} catch (LockedAccountException e) {
			msg = e.getMessage();
			redirectAttributes.addFlashAttribute("message", msg);
			logger.error("账号已被锁定. The account for username " + loginName + " was locked.");
		} catch (DisabledAccountException e) {
			msg = "账号已被禁用！";
			redirectAttributes.addFlashAttribute("message", msg);
			logger.error("账号已被禁用. The account for username " + loginName + " was disabled.");
		} catch (ExpiredCredentialsException e) {
			msg = "账号已过期！";
			redirectAttributes.addFlashAttribute("message", msg);
			logger.error("账号已过期. the account for username " + loginName + "  was expired.");
		} catch (UnknownAccountException e) {
			msg = "账号不存在！ ";
			redirectAttributes.addFlashAttribute("message", msg);
			logger.error("账号不存在. There is no user with username of " + loginName);
		}
		return "redirect:/";
	}
	
    /**
     * 
     * @Description: 跳转到首页
     * @author HUCHAO
     * @date 2018年4月3日 上午10:53:25
     * @param mav
     * @return
     */
	@ApiOperation(value="登录成功", httpMethod="GET")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView mav) {
		Subject subject = SecurityUtils.getSubject(); 
	    if (subject != null && subject.isAuthenticated()) {  
	    	ShiroUser shiroUser = getShiroUser(subject);
	    	mav.addObject("userId", shiroUser.getId());//用户ID
	    	mav.addObject("menulist", shiroUser.getMenuList());//菜单
//	    	mav.addObject("menulist", checkResource(shiroUser.getMenuList(),subject));//菜单（二次校验废弃）
	    	mav.addObject("realName", shiroUser.getRealName());//用户真实姓名
	    	mav.addObject("lastChgPwdTime", shiroUser.getLastChgPwdTime());//最后修改密码时间
		    mav.setViewName(INDEX_PAGE);
	    }else{
	    	mav.setViewName("redirect:/");
	    }
	    return mav;
	}
    
    /**
	 * 退出
	 * @return
	 */
	@ApiOperation(value="退出", httpMethod="GET")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request){   
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息  
		Subject subject = SecurityUtils.getSubject(); 
	    if (subject != null && subject.isAuthenticated()) {  
	    	String userId = getShiroUserId(subject);//当前用户ID
	    	sysUserService.updateUserLoginInfoByUserId(request, userId); //修改用户登录时间
	        subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存  
	    }  
        return "redirect:/";  
    }  

}
