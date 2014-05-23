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

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * 
 * TODO.
 *
 * @author huanglicong
 * @version V1.0
 */
public class SequencesGenPlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> warnings) {

		return true;
	}

	@Override
	public boolean sqlMapInsertElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {

		genSelectKey(element, introspectedTable);
		return true;
	}

	@Override
	public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {

		genSelectKey(element, introspectedTable);
		return true;
	}

	public void genSelectKey(XmlElement element,
			IntrospectedTable introspectedTable) {

		String name = introspectedTable.getFullyQualifiedTable()
				.getIntrospectedTableName();
		if (name == null) {
			return;
		}
		if (introspectedTable.getPrimaryKeyColumns().size() != 1) {
			return;
		}
		String sq = getProperties().getProperty(name.toUpperCase());
		if (sq == null) {
			return;
		}
		System.out.println("[key]==========>>>" + name);
		System.out.println("[key]==========>>>" + sq);
		IntrospectedColumn primarykey = introspectedTable
				.getPrimaryKeyColumns().get(0);
		String primarykeyName = primarykey.getJavaProperty();
		// <selectKey keyProperty="id" resultType="long" order="BEFORE">
		// SELECT SQ_SCHEDULE.NEXTVAL FROM DUAL
		// </selectKey>
		XmlElement selectKey = new XmlElement("selectKey");
		selectKey.addAttribute(new Attribute("keyProperty", primarykeyName));
		selectKey.addAttribute(new Attribute("resultType", primarykey
				.getFullyQualifiedJavaType().getFullyQualifiedName()));
		selectKey.addAttribute(new Attribute("order", "BEFORE"));
		selectKey.addElement(new TextElement(sq));
		element.addElement(0, selectKey);
	}
}
