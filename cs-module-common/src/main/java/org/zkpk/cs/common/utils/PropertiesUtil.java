package org.zkpk.cs.common.utils;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public static String getProperty(String propertiesName, String key){
		try{
			Properties pros = new Properties();
			InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("config/" + propertiesName);
			pros.load(in);
			in.close();
			return pros.getProperty(key);
		}catch (Exception e) {
			return "";
		}
	}
	
}
