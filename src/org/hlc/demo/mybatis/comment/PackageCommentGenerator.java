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
package org.hlc.demo.mybatis.comment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.xml.XmlElement;

import com.google.common.base.Strings;
import com.google.common.io.CharStreams;

/**
 * 
 * TODO.
 *
 * @author huanglicong
 * @version V1.0
 */
public class PackageCommentGenerator implements CommentGenerator {

	public static final SimpleDateFormat LONG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void addJavaFileComment(CompilationUnit compilationUnit) {
		String packageComment = CodetemplatesLoader.getInstance().get("filecomment_context");
		try {
			List<String> lines = CharStreams.readLines(CharStreams.newReaderSupplier(packageComment));
			if (lines == null) {
				return;
			}
			boolean isfirst = true;
			for (String line : lines) {
				line = line.trim();
				if (Strings.isNullOrEmpty(line)) {
					continue;
				}
				if (!isfirst) {
					line = " " + line;
				}
				compilationUnit.addFileCommentLine(line);
				isfirst = false;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addConfigurationProperties(Properties properties) {

	}

	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

	}

	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable) {

		// TODO Auto-generated method stub

	}

	@Override
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {

		// TODO Auto-generated method stub

	}

	@Override
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {

		// TODO Auto-generated method stub

	}

	@Override
	public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {

	}

	@Override
	public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

	}

	@Override
	public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

	}

	@Override
	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {

	}

	@Override
	public void addComment(XmlElement xmlElement) {

	}

	@Override
	public void addRootComment(XmlElement rootElement) {

	}

}
