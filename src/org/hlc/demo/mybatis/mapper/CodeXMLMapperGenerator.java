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

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.LinkedHashMap;
import java.util.Map;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;

/**
 * 
 * 自定义mapper生成器.
 *
 * @author huanglicong
 * @version V1.0
 */
public class CodeXMLMapperGenerator extends XMLMapperGenerator {

	private static final Map<IntrospectedTable, Document> map = new LinkedHashMap<IntrospectedTable, Document>();

	@Override
	public Document getDocument() {

		Document document = super.getDocument();
		map.put(getIntrospectedTable(), document);
		return document;
	}

	@Override
	protected XmlElement getSqlMapElement() {
		FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
		progressCallback.startTask(getString("Progress.12", table.toString())); //$NON-NLS-1$
		XmlElement answer = new XmlElement("mapper"); //$NON-NLS-1$
		String namespace = introspectedTable.getMyBatis3SqlMapNamespace();

		String value = namespace;
		int index = value.lastIndexOf(".");
		value = value.substring(0, index);
		String className = namespace.substring(index).replace("BaseDao", "Dao");
		namespace = value + className;

		//$NON-NLS-1$
		answer.addAttribute(new Attribute("namespace", namespace));

		context.getCommentGenerator().addRootComment(answer);

		addResultMapWithoutBLOBsElement(answer);
		addResultMapWithBLOBsElement(answer);
		addExampleWhereClauseElement(answer);
		addMyBatis3UpdateByExampleWhereClauseElement(answer);
		addBaseColumnListElement(answer);
		addBlobColumnListElement(answer);
		addSelectByExampleWithBLOBsElement(answer);
		addSelectByExampleWithoutBLOBsElement(answer);
		addSelectByPrimaryKeyElement(answer);
		addDeleteByPrimaryKeyElement(answer);
		addDeleteByExampleElement(answer);
		addInsertElement(answer);
		addInsertSelectiveElement(answer);
		addCountByExampleElement(answer);
		addUpdateByExampleSelectiveElement(answer);
		addUpdateByExampleWithBLOBsElement(answer);
		addUpdateByExampleWithoutBLOBsElement(answer);
		addUpdateByPrimaryKeySelectiveElement(answer);
		addUpdateByPrimaryKeyWithBLOBsElement(answer);
		addUpdateByPrimaryKeyWithoutBLOBsElement(answer);

		return answer;
	}

	public static Map<IntrospectedTable, Document> getMap() {

		return map;
	}

}
