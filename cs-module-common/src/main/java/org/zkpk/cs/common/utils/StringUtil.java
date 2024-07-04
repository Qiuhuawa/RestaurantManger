package org.zkpk.cs.common.utils;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class StringUtil {

	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * 
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr) {
		int i = 0;
		String TempStr = valStr;
		String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
		valStr = valStr + ",";
		while (valStr.indexOf(',') > 0) {
			returnStr[i] = valStr.substring(0, valStr.indexOf(','));
			valStr = valStr.substring(valStr.indexOf(',') + 1, valStr.length());

			i++;
		}
		return returnStr;
	}

	/**
	 * @Description: 判断对象是否为空
	 * @author ZXN
	 * @date 2015-9-12 下午02:16:17
	 * @param object
	 * @return true=空,false=不为空;
	 */
	public static boolean isEmpty(Object object) {
		if (object == null || "".equals(object) || "undefined".equals(object) || "null".equals(object)
				|| (object instanceof java.util.List && (((List<?>) object).size() == 0))
				|| (object instanceof java.util.Map && (((Map<?, ?>) object).size() == 0))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Description: 判断字符串是否为空
	 * @author ZXN
	 * @date 2015-9-12 下午02:16:17
	 * @param object
	 * @return true=空,false=不为空;
	 */
	public static boolean isNumEmpty(Object object) {
		if (object == null || "".equals(object) || "undefined".equals(object) || "null".equals(object) || object.equals("0")
				|| Double.parseDouble(object.toString()) == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Description 前台get请求中文乱码
	 * @author ZXN
	 * @date 2016-2-19
	 * @param str 前台乱码值
	 * @return 正常utf-8
	 */
	public static String encodeStr(String str) {
		try {
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @Description: 截取字符串长度
	 * @author ZhangXiaoNan
	 * @date 2016年11月8日 下午4:31:24
	 * @param str 要截取的字符串
	 * @return 小于20长度,返回原字符串,大于20长度返回前20长度+ ．．．
	 */
	public static String subStr(String str) {
		if (StringUtil.isEmpty(str) || str.length() < 20) {
			return str;
		}
		return str.substring(0, 20) + "．．．";
	}

	/**
	 * 首字母变大写
	 */
	public static String firstCharToUpperCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'a' && firstChar <= 'z') {
			char[] arr = str.toCharArray();
			arr[0] -= ('a' - 'A');
			return new String(arr);
		}
		return str;
	}

	/**
	 * 字符串为 null 或者为 "" 时返回 true
	 */
	public static boolean isBlank(String str) {
		return str == null || "".equals(str.trim()) ? true : false;
	}

	/**
	 * 字符串不为 null 而且不为 "" 时返回 true
	 */
	public static boolean notBlank(String str) {
		return str == null || "".equals(str.trim()) ? false : true;
	}

	public static boolean notBlank(String[] strings) {
		if (strings == null)
			return false;
		for (String str : strings)
			if (str == null || "".equals(str.trim()))
				return false;
		return true;
	}

	public static boolean notNull(Object str) {
		return str == null ? false : true;
	}
	
	public static boolean isNotNull(String str) {
		if (str != null && !"".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean notNull(Object[] paras) {
		if (paras == null)
			return false;
		for (Object obj : paras)
			if (obj == null)
				return false;
		return true;
	}

	public static String replaceValue(String value, Map<String, String> map) {
		if (StringUtil.isBlank(value)) {
			return null;
		}
		for (Entry<String, String> entry : map.entrySet()) {
			value = value.replace(entry.getKey(), entry.getValue());
		}
		return value;
	}

	// 将字符串转移为ASCII码
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	/**
	 * @Description: 格式化文本 回车 空格等>br
	 * @author ZhangXiaoNan
	 * @date 2017年9月21日 上午10:12:07
	 * @param text
	 * @return
	 */
	public static String formatTextAarea(String text) {
		String title = text;
		title = title.replaceAll("(\r\n|\r|\n|\n\r)", "<br/>");
		title = title.replaceAll(" ", "&nbsp");
		String tempArray[] = title.split("<br/>");
		String result = "";
		for (String str : tempArray) {
			result += "<p>" + str + "</p>";
		}
		return result;
	}
	
	/**
	 * 
	 * @Description: 获取完整uuid
	 * @author HUCHAO
	 * @date 2018年3月27日 上午10:55:12
	 * @return
	 */
	public static String getUUId() {
        return UUID.randomUUID().toString();
    }
	
	/**
	 * 
	 * @Description: 生成uuid
	 * @author HUCHAO
	 * @date 2018年3月27日 上午10:09:20
	 * @return
	 */
	public static String getDateUUId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
	
	/**
	 * 
	 * @Description: 根据字符串生成uuid
	 * @author HUCHAO
	 * @date 2018年3月27日 上午10:09:25
	 * @param str
	 * @return
	 */
	public static String getDateUUId(String str) {
        return UUID.nameUUIDFromBytes(str.getBytes()).toString().replaceAll("-", "");
    }
	
	/**
	 * 
	 * @Description: 获取固定位数的验证码
	 * @author HUCHAO
	 * @date 2018年3月27日 上午10:11:12
	 * @param charCount
	 * @return
	 */
	public static String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0,10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }
	
	/**
	 * 
	 * @Description: 随机int
	 * @author HUCHAO
	 * @date 2018年3月27日 上午10:11:16
	 * @param from
	 * @param to
	 * @return
	 */
	public static int randomInt(int from, int to) {
    	SecureRandom random = new SecureRandom();//随机数
        return from + random.nextInt(to - from);
    }
	
	/**
	 * 
	 * @Description: 完成度
	 * @author HUCHAO
	 * @date 2018-06-19 11:17:11
	 * @param completeNum
	 * @param totalNum
	 * @return
	 * @throws Exception
	 */
	public static String completionDegree(int completeNum, int totalNum) throws Exception {
		double accuracy_num = 0;
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
		// 可以设置精确几位小数
		df.setMaximumFractionDigits(0);
		// 模式 例如四舍五入
		df.setRoundingMode(RoundingMode.HALF_UP);
		if (totalNum != 0) {
			accuracy_num = ((double) completeNum / (double) totalNum) * 100;
		}
		return df.format(accuracy_num) + "%";
	}
	
}
