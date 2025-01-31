package org.zkpk.cs.common.utils;

import java.util.regex.Pattern;

public class ValidatorUtil {
	
	/**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_LOGINNAME = "^[0-9a-zA-Z_]{4,20}$";

    /**
     * 正则表达式：验证姓名
     */
    public static final String REGEX_USERNAME = "^[\u4e00-\u9fa5\\w\\（\\）]{1,30}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[0-9a-zA-Z\\.$@]{6,20}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$";

    /**
     * 正则表达式：验证固话
     */
    public static final String REGEX_PHONE = "^(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})|(0\\d{3}-\\d{8})$";

    /**
     * 正则表达式：验证qq
     */
    public static final String REGEX_QQ = "^[1-9][0-9]{4,} $";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^[A-Za-z0-9][\\w\\-\\.]{1,12}@([\\w\\\\-]+\\.)+[\\w]{2,3}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{17}(x|X|\\d)$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    
    /**
     * 校验用户名
     *
     * @param loginname
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isLoingName(String loginname) {
        return Pattern.matches(REGEX_LOGINNAME, loginname);
    }
    
    /**
     * 校验姓名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUserName(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验固话
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone){
        return Pattern.matches(REGEX_PHONE, phone);
    }

    /**
     * 校验qq
     * @param qq
     * @return
     */
    public static boolean isQQ(String qq){
        return Pattern.matches(REGEX_QQ, qq);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

}
