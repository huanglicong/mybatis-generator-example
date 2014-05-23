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
package org.hlc.demo.mybatis.mapper;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Document;

/**
 * 
 * TODO.
 *
 * @author huanglicong
 * @version V1.0
 */
public class MapperConveterPlugin extends PluginAdapter {

	private String searchString;
	private String replaceString;
	private Pattern pattern;

	public boolean validate(List<String> warnings) {

		searchString = properties.getProperty("searchString"); //$NON-NLS-1$
		replaceString = properties.getProperty("replaceString"); //$NON-NLS-1$

		boolean valid = stringHasValue(searchString) && stringHasValue(replaceString);

		if (valid) {
			pattern = Pattern.compile(searchString);
		} else {
			if (!stringHasValue(searchString)) {
				warnings.add(getString("ValidationError.18", //$NON-NLS-1$
						"RenameExampleClassPlugin", //$NON-NLS-1$
						"searchString")); //$NON-NLS-1$
			}
			if (!stringHasValue(replaceString)) {
				warnings.add(getString("ValidationError.18", //$NON-NLS-1$
						"RenameExampleClassPlugin", //$NON-NLS-1$
						"replaceString")); //$NON-NLS-1$
			}
		}

		return valid;
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		String oldType = introspectedTable.getMyBatis3JavaMapperType();
		Matcher matcher = pattern.matcher(oldType);
		oldType = matcher.replaceAll(replaceString);
		System.out.println("[append xml]========>>" + oldType);
		introspectedTable.setMyBatis3JavaMapperType(oldType);

	}

	public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {

		String name = introspectedTable.getFullyQualifiedTable().getIntrospectedTableName();
		System.out.println("[append xml]========>>" + name);
		if (name == null) {
			return true;
		}
		String extendedFile = getProperties().getProperty(name.toUpperCase());
		if (extendedFile == null) {
			return true;
		}

		Document document = CodeXMLMapperGenerator.getMap().get(introspectedTable);
		if (document == null) {
			return true;
		}

		DocumentAppender appender = new DocumentAppender(extendedFile, document);
		appender.execute();
		return true;
	}

}
