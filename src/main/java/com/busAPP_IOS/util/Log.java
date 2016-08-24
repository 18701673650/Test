package com.busAPP_IOS.util;

import org.apache.log4j.Logger;

public class Log {

	private static Logger Log = Logger.getLogger(Log.class.getName());
	//定义测试用例开始执行的方法，在日志中打印测试用例开始执行的信息
	public static void startTestCase(String testCaseName){
		Log.info("------------------------                  \""+testCaseName+"\"开始执行                  ------------------------");
	}
	
	//定义测试用例执行完毕后的方法，在日志中打印测试用例执行完毕的信息
	public static void endTestCase(String testCaseName){
		Log.info("------------------------                  \""+testCaseName+"\"执行完毕                  ------------------------");
	}
	
	
	//定义打印info级别的日志
	public static void info(String message){
		Log.info(message);
	}
	
	//定义打印debug级别的日志
	public static void debug(String message){
		Log.debug(message);
	}
	
	//定义打印debug级别的日志
	public static void error(String message){
		Log.error(message);
	}
}
