package com.jianq.manager.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

	private static Logger getLog(@SuppressWarnings("rawtypes") Class className) {
		return LoggerFactory.getLogger(className);
	}

	/**
	 * 日志打印：controller之前
	 * 
	 * @param className
	 *            ：当前类的类对象
	 * @param methodDesc
	 *            ：方法名称或描述
	 * @param params
	 *            ：参数
	 */
	@SuppressWarnings("rawtypes")
	public static void before(Class className, String methodDesc, String params) {
		getLog(className).info("APP当前请求接口是：{}，参数是：{}", methodDesc, params);
	}

	/**
	 * 日志打印：controller之后
	 * 
	 * @param className
	 *            ：当前类的类对象
	 * @param result
	 *            ：返回结果
	 */
	@SuppressWarnings("rawtypes")
	public static void after(Class className, String result) {
		getLog(className).info("APP接口返回值是：{}", result);
	}

	/**
	 * 发送给第三方
	 * 
	 * @param url
	 *            ：方法名称或描述
	 * @param params
	 *            ：参数
	 * @param result
	 *            ：返回结果
	 */
	public static void sendToThirdParty(String url, String params, String result) {
		LoggerFactory
				.getLogger("第三方日志")
				.info("\n ################################## 请求第三方日志 【开始】 ###############################");
		LoggerFactory.getLogger("第三方日志").info(
				"\n\n 接口URL:“{}”\n 请求参数:{} \n 返回值:{} \n", url, params, result);
		LoggerFactory
				.getLogger("第三方日志")
				.info("\n ################################## 请求第三方日志 【结束】 ###############################");
	}
}
