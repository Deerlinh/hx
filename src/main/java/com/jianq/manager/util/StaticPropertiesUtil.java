package com.jianq.manager.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统业务参数配置
 */
public class StaticPropertiesUtil {
	/**
	 * 共同的配置文件
	 */
	private static final String COMMON_PROPERTIES_FILE_NAME = "/properties/common.properties";
	private static Properties commonProperties = null;

	// 当前的配置文件名称在公共配置文件中的key
	private static final String PART_FILE_NAME_KEY = "current.profiles.active";

	/**
	 * 部分的多环境配置文件
	 */
	private static String PART_PROPERTIES_FILE_NAME = "";
	private static Properties partProperties = null;
	private static final Logger log = LoggerFactory
			.getLogger(StaticPropertiesUtil.class);

	/**
	 * 初始化
	 */
	static {
		try {
			// 初始化公共的配置文件
			commonProperties = new Properties();
			// 解决中文获取乱码
			commonProperties.load(new InputStreamReader(
					StaticPropertiesUtil.class
							.getResourceAsStream(COMMON_PROPERTIES_FILE_NAME),
					"UTF-8"));

			// 获取私有多环境配置文件，并初始化
			PART_PROPERTIES_FILE_NAME = commonProperties
					.getProperty(PART_FILE_NAME_KEY);
			if (StringUtil.isBlank(PART_PROPERTIES_FILE_NAME)) {
				log.info("无私有的多环节配置文件");
			} else {
				partProperties = new Properties();
				partProperties.load(new InputStreamReader(
						StaticPropertiesUtil.class
								.getResourceAsStream("/properties/"
										+ PART_PROPERTIES_FILE_NAME), "UTF-8"));
			}
		} catch (IOException e) {
			log.error("初始化系统参数失败!", e);
		}
	}

	/**
	 * 得到系统业务配置参数值
	 * 
	 * @param key
	 * @return 如果不存在，返回null
	 */
	public static String getValue(String key) {
		if (partProperties == null) {
			return commonProperties.getProperty(key);
		} else {
			String value = partProperties.getProperty(key);
			if (StringUtil.isBlank(value)) {
				// 私有的配置文件为空，再去公共的中取 。不然以私有的多环境配置文件为准
				value = commonProperties.getProperty(key);
			}
			return value;
		}
	}

	public static void main(String[] args) {
		System.out.println(getValue(""));
	}

}
