package org.hlc.demo.mybatis.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertieUtils {

	static Properties prop = new Properties();
	static {
		InputStream in = PropertieUtils.class.getResourceAsStream("/generatorConfig.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String get(String name) {
		return prop.getProperty(name);
	}

}
