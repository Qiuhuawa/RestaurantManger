package org.zkpk.cs.shiro.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import org.zkpk.cs.common.utils.DigestsUtil;
import org.zkpk.cs.common.utils.EncodeUtil;


/**
 * shiro密码加密配置
 * @author 
 *
 */
public class PasswordHash implements InitializingBean {
	
	private String algorithmName;
	
	private int hashIterations;
	
	private boolean storedCredentialsHexEncoded;
	
	private int SALT_SIZE = 16;

	public String getAlgorithmName() {
		return algorithmName;
	}
	
	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}
	
	public int getHashIterations() {
		return hashIterations;
	}
	
	public void setHashIterations(int hashIterations) {
		this.hashIterations = hashIterations;
	}

	public boolean isStoredCredentialsHexEncoded() {
		return storedCredentialsHexEncoded;
	}

	public void setStoredCredentialsHexEncoded(boolean storedCredentialsHexEncoded) {
		this.storedCredentialsHexEncoded = storedCredentialsHexEncoded;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.hasLength(algorithmName, "algorithmName mast be MD5、SHA-1、SHA-256、SHA-384、SHA-512");
	}
	
	/**
	 * 生成加密字符串
	 * @param source
	 * @param salt
	 * @return
	 */
	public String toEncode(Object source, Object salt) {
		if(storedCredentialsHexEncoded){
			return new SimpleHash(algorithmName, source, salt, hashIterations).toHex();
		}else{
			return new SimpleHash(algorithmName, source, salt, hashIterations).toBase64();
		}
	}
	
	/**
	 * 密码加密
	 * @param plainPassword
	 * @return
	 */
	public HashPassword encryptPassword(String plainPassword) {
		HashPassword result = new HashPassword();
		byte[] salt = DigestsUtil.generateSalt(SALT_SIZE);
		result.salt = EncodeUtil.encodeHex(salt);
		String hashPassword = toEncode(plainPassword.getBytes(), salt);
		result.password = hashPassword;
		return result;
	}
	
	/**
	 * 新密码用旧盐度加密（用于比较新密码和旧密码是否相同）
	 * @param plainPassword
	 * @param oldSalt
	 * @return
	 */
	public HashPassword encryptNewPasswordByOldSalt(String plainPassword, String oldSalt) {
		HashPassword result = new HashPassword();
		byte[] salt = EncodeUtil.decodeHex(oldSalt);
		String hashPassword = toEncode(plainPassword.getBytes(), salt);
		result.password = hashPassword;
		return result;
	}
}
