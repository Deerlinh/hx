package com.jianq.manager.context;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.support.ResourcePropertySource;

/**
 * 为了spring动态的import配置文件（配置文件的名称从common.properties读取）
 * 
 * @author hp
 * @created 2017-4-25
 */
public class CustomerApplicationContextInitializer implements
		ApplicationContextInitializer<ConfigurableApplicationContext> {
	private static Logger logger = LoggerFactory
			.getLogger(CustomerApplicationContextInitializer.class);

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		ResourcePropertySource propertySource = null;
		try {
			propertySource = new ResourcePropertySource(
					"classpath:properties/common.properties");
		} catch (IOException e) {
			logger.error("properties/common.properties is not exists");
		}
		applicationContext.getEnvironment().getPropertySources()
				.addFirst(propertySource);

	}
}
