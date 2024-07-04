package org.zkpk.cs.common.utils;

import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.JCERSAPublicKey;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

public class RSAUtil {

	/** 可以先注册到虚拟机中,再通过名称使用;也可以不注册,直接传入使用 */
	public static final Provider pro = new BouncyCastleProvider();
	
	private static final KeyPair keyPair = initKey();
	
	/** 种子,改变后,生成的密钥对会发生变化 */
	private static final String seedKey = "random";

	private static final String charSet = "UTF-8";
	
	private static PrivateKey privateKey = null;
	private static PublicKey publicKey = null;
	private static String jsPublicKey = null;
	
	/**
	 * 生成密钥对
	 * @return 
	 * @throws Exception
	 */
	private static KeyPair initKey(){
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", pro);
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(seedKey.getBytes());
			generator.initialize(1024, secureRandom);
			return generator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("初始化密匙对失败");
		}
	}

	/**
	 * 解密
	 */
	public static byte[] decrypt(byte[] encrypted) throws Exception {
		long start = System.currentTimeMillis();
		Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", pro);
		cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
		byte[] plainText  = cipher.doFinal(encrypted);
		long end = System.currentTimeMillis();
		System.out.println("decrypt use time " + (end - start) + "");
		return plainText;
	}

	/**
     * 生成public key
     * @return
     */
    public static String generateBase64PublicKey(){
        RSAPublicKey key = (RSAPublicKey)keyPair.getPublic();
        return new String(Base64.encode(key.getEncoded()));
    }
     
    /**
     * 解密
     * @param string
     * @return
     * @throws Exception 
     */
    public static String decryptBase64(String string) throws Exception {
        return new String(decrypt(Base64.decode(string)));
    }
    
	/**
	 * js加密时使用的公钥字符串
	 * <p><b>注意：</b>
	 * 生成的密钥对的值与 种子（seedKey）、虚拟机实现等都有关系，不同的机器生成的密钥值可能不同。
	 * 在实际测试时发现，同样的环境，有些机器每次生成的密钥值也不同，比如每次重启服务器后值不同。
	 * 所以在实际生产环境中使用时，该值需要通过服务器端输出到客户端。
	 * 如果有多台服务器，可能每台服务器的值不同，所以需要有类似F5的策略，保证多次请求路由到一台服务器上。
	 */
	public static String getJsPublicKey() {
		if (jsPublicKey == null) {
			JCERSAPublicKey jce = (JCERSAPublicKey) publicKey;
			jsPublicKey = jce.getModulus().toString(16);
		}
		return jsPublicKey;
	}
	
	/**
	 * 解密js加密后的值
	 */
	public static String decodeJsValue(String jsValue) throws Exception {
		byte[] input = Hex.decode(jsValue);
		byte[] raw = decrypt(input);
		// 标志位为0之后的是输入的有效字节
		int i = raw.length - 1;
		while (i > 0 && raw[i] != 0) {
			i--;
		}
		i++;
		byte[] data = new byte[raw.length - i];
		for (int j = i; j < raw.length; j++) {
			data[j - i] = raw[j];
		}
		return new String(data, charSet);
	}

	public static void main(String[] args) throws Exception {
		//注意：需要使用该值替换test.html中的公钥值
		System.out.println("js中使用的公钥字符串" + getJsPublicKey());
		// js加密后的值
		String de = "08f7e292ccb4c73a981569a9c2dbf2b9c0c2cf615967282863e6e358432af288f1f026ed91a8ff5f6579ac246af9ce1f94f85e92b8a926627b95e6bd05b00b80a5548e9ce1a9bb2a20073cce629936ab9e27021af7370c2664065107a702c1805a4ec131a3573007213da3e390221053867074a427ffc28aa642fe2099ad7332";
		System.out.println(decodeJsValue(de));
	}
	
	/**
	 * 根据Base64编码的公钥值生成公钥对象
	 * <p>
	 * 测试时使用，可以用于从证书文件中的公钥生成公钥对象。如果不涉及到证书操作，可以忽略该方法。
	 */
	public static PublicKey getPublicRSAKey(String key) throws Exception {
		X509EncodedKeySpec x509 = new X509EncodedKeySpec(Base64.decode(key));
		KeyFactory kf = KeyFactory.getInstance("RSA", pro);
		return kf.generatePublic(x509);
	}

	/**
	 * 根据Base64编码的私钥值生成私钥对象
	 * <p>
	 * 测试时使用，可以用于从证书文件中的私钥生成私钥对象。如果不涉及到证书操作，可以忽略该方法。
	 */
	public static PrivateKey getPrivateRSAKey(String key) throws Exception {
		PKCS8EncodedKeySpec pkgs8 = new PKCS8EncodedKeySpec(Base64.decode(key));
		KeyFactory kf = KeyFactory.getInstance("RSA", pro);
		return kf.generatePrivate(pkgs8);
	}
	
	/**
	 * 加密
	 */
	public static byte[] encrypt(String input) throws Exception {
		long start = System.currentTimeMillis();
		Cipher cipher = Cipher.getInstance("RSA", pro);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] re = cipher.doFinal(input.getBytes(charSet));
		long end = System.currentTimeMillis();
		System.out.println("encrypt use time " + (end - start) + "");
		return re;
	}	
	
	private final void writeObject(ObjectOutputStream out) throws NotSerializableException {
	    throw new java.io.NotSerializableException("This object cannot be serialized");
	}
	
	private final void readObject(ObjectInputStream in) throws NotSerializableException {
	    throw new java.io.NotSerializableException("This object cannot be deserialized");
	}
	
	@Override
	public final Object clone() throws CloneNotSupportedException { 
	    return super.clone();
	}

}

