package org.zkpk.cs.shiro.utils;

/**
 * 密码处理类
 * @author huchao 2017-05-03
 *
 */
public class HashPassword {
	
	//加密盐度
	public String salt;
	
	//密码
	public String password;

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
