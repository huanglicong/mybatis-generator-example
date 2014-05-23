/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hlc.demo.mybatis.utils;

import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.jautodoc.templates.wrapper.DateWrapper;
import net.sf.jautodoc.templates.wrapper.PropertyWrapper;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * 模板引擎工具类.
 *
 * @author huanglicong
 * @version V1.0
 */
public class VelocityUtils {

	public static final String KEY_ELEMENT = "e";
	public static final String KEY_PROPERTIES = "p";
	public static final String KEY_USER = "user";
	public static final String KEY_DATE = "date";
	public static final String KEY_TIME = "time";
	public static final String KEY_YEAR = "year";
	public static final String KEY_PROJECT = "project_name";
	public static final String KEY_PACKAGE = "package_name";
	public static final String KEY_FILE = "file_name";
	public static final String KEY_TYPE = "type_name";

	private static VelocityEngine ve;
	private static VelocityContext context;

	static {
		ve = new VelocityEngine();
		ve.init();
		context = new VelocityContext();
		context.put(KEY_PROPERTIES, new PropertyWrapper());
		context.put(KEY_USER, new PropertyWrapper("user.name", null));

		context.put(KEY_DATE, new DateWrapper(DateWrapper.DATE));
		context.put(KEY_TIME, new DateWrapper(DateWrapper.TIME));
		context.put(KEY_YEAR, new DateWrapper(DateWrapper.YEAR));
	}

	public static String evaluate(String source, Map<String, Object> local) {
		StringWriter writer = new StringWriter();
		for (Entry<String, Object> item : local.entrySet()) {
			context.remove(item.getKey());
			context.put(item.getKey(), item.getValue());
		}
		ve.evaluate(context, writer, "", source);
		return writer.toString();
	}

}
