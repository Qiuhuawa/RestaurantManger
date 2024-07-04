package org.zkpk.cs.controller.auth;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/test")
public class Testcontroller {
	
    @RequestMapping("/hello")
    public String hello(Model model) {
    	model.addAttribute("name","张三");
    	model.addAttribute("age","30");
    	return "main/layout";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password")String password,Model model) {
 
        // 获取当前用户
        Subject subject = SecurityUtils.getSubject();
        // 封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 执行登录方法
        try {
            subject.login(token);
            return "2021250360043-程超";
        } catch(UnknownAccountException e) { //用户名不存在
            return "login";
        } catch (IncorrectCredentialsException e) { // 密码不存在
            return "login";
        }
    }
}