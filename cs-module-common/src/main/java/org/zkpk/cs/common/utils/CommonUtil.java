package org.zkpk.cs.common.utils;

import java.util.Random;

/**
 * 
 * @Description: 高频方法工具类
 * @author HUCHAO
 * @date 2018-10-19 14:26:36
 */
public class CommonUtil {

	/*
	 * *
	 * @Description 获取指定位数的随机数
	 * @Param [length]
	 * @Return java.lang.String
	 */
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
