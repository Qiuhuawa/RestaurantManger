package org.zkpk.cs.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PythonUtil {
	
	//日志
    private static final Logger logger = LoggerFactory.getLogger(PythonUtil.class);
	
	public static String runPythonForExcute(String pythonName, Map<String, Object> parameterMap) {
		String line = null;
		InputStream in = null;
		InputStreamReader inr = null;
		BufferedReader br = null;
		String result = "0";
        try {///usr/local/python3/bin/python3   /opt/python/
        	String pythonPath = PropertiesUtil.getProperty("config.properties", "pythonPath") + " ";
        	String filePath = PropertiesUtil.getProperty("config.properties", "pythonFilePath") + pythonName + " ";
        	String fileType = (String)parameterMap.get("filetype");
        	if ("one".equals(fileType)) {
        		Process process = Runtime.getRuntime().exec(pythonPath + filePath + (String)parameterMap.get("filepath"));
        		in = process.getInputStream();
        		inr = new InputStreamReader(in);
        		br = new BufferedReader(inr);
    			while ((line = br.readLine()) != null) {
    				System.out.println(line);
    				if (line != null) {
    					result = line.split(" ")[2];
    				}
    			}
    			br.close();
    			int re = process.waitFor();
    			System.out.println(re == 1 ? "----状态码1----运行失败" : "----状态码0----运行成功");
        	} else if ("list".equals(fileType)) {
        		System.out.println(pythonPath + filePath + (List)parameterMap.get("filepath"));
        		Process process = Runtime.getRuntime().exec(pythonPath + filePath + (List)parameterMap.get("filepath"));
        		in = process.getInputStream();
        		inr = new InputStreamReader(in);
        		br = new BufferedReader(inr);
    			while ((line = br.readLine()) != null) {
    				System.out.println(line);
    				if (line != null) {
    					result = line.split(" ")[2];
    				}
    			}
    			br.close();
    			int re = process.waitFor();
    			System.out.println(re == 1 ? "----状态码1----运行失败" : "----状态码0----运行成功");
        	}
		} catch (Exception e) {
			logger.error("调用python脚本出错了，错误信息：" + e.getMessage());
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("调用python脚本，关闭出错了，错误信息：" + e.getMessage());
				}
			}
			if(inr != null) {
				try {
					inr.close();
				} catch (IOException e) {
					logger.error("调用python脚本，关闭出错了，错误信息：" + e.getMessage());
				}
			}
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("调用python脚本，关闭出错了，错误信息：" + e.getMessage());
				}
			}
		}
		return result;
	}
	
//	public static String runPythonForJython(String pythonName, Map<String, Object> paraMap) {
//		PythonInterpreter interpreter = new PythonInterpreter();
//        interpreter.execfile("D:\\add.py");
//        // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
//        PyFunction pyFunction = interpreter.get("add", PyFunction.class);
//        int a = 5, b = 10;
//        //调用函数，如果函数需要参数，在Java中必须先将参数转化为对应的“Python类型”
//        PyObject pyobj = pyFunction.__call__(new PyInteger(a), new PyInteger(b)); 
//        System.out.println("the anwser is: " + pyobj);
//		return null;
//	}
	
	public static void main(String[] args) {
//		runPythonForJython(null,null);
		List<String> arrylist = new ArrayList<String>();
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		arrylist.add("/opt/sow/upload/attachment/92b45ae2-4e4f-4e06-828b-aa28af7b6db9.txt");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("filepath", arrylist);
		parameterMap.put("filetype", "list");
		runPythonForExcute("list_test", parameterMap);
		
	}

}
