package com.geovis.jg.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

//import org.springframework.core.io.ClassPathResource;


//@Component
//@PropertySource("tablename.properties")
//@ConfigurationProperties(prefix = "table")
public class PropertiesUtils extends PropertyPlaceholderConfigurer {
	public static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
	private static Map<String, String> ctxPropertiesMap;

	public PropertiesUtils(){
		this.setIgnoreResourceNotFound(true);
		final List<ClassPathResource> resourceList = new ArrayList<ClassPathResource>();
		resourceList.add( new ClassPathResource("tablename.properties"));
		this.setLocations(resourceList.toArray(new org.springframework.core.io.Resource[]{}));
	}

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
		super.processProperties(beanFactory, props);
		// 读取propertys到ctxPropertiesMap属性中
		ctxPropertiesMap = new ConcurrentHashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}


	public static String getProperty(String name) {
		String str = "";
		try {
			str = ctxPropertiesMap.get(name);
		}catch (Exception exception){
			//exception.printStackTrace();
			//logger.error(exception.getMessage());
		}
		return str ;
	}

}
