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
import java.util.List;
import java.util.Map;

import org.hlc.demo.mybatis.utils.VelocityUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;

/**
 * 
 * 注释插件.
 *
 * @author huanglicong
 * @version V1.0
 */
public class ClassCommentPlugin extends PluginAdapter {

	/**
	 * Instantiates a new class comment plugin.
	 */
	public ClassCommentPlugin() {

	}

	/**
	 * 生成类注释.
	 *
	 * @param introspectedTable the introspected table
	 * @param interfaze the interfaze
	 */
	public void classGenerated(IntrospectedTable introspectedTable, Interface interfaze) {

		Map<String, Object> local = Maps.newLinkedHashMap();
		local.put("todo", new ClassWrapper(introspectedTable));
		local.put("tags", "");

		String classComment = CodetemplatesLoader.getInstance().get("typecomment_context");
		classComment = VelocityUtils.evaluate(classComment, local);
		try {
			List<String> lines = CharStreams.readLines(CharStreams.newReaderSupplier(classComment));
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
				interfaze.addJavaDocLine(line);
				isfirst = false;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 生成类注释.
	 *
	 * @param introspectedTable the introspected table
	 * @param topLevelClass the top level class
	 */
	public void classGenerated(IntrospectedTable introspectedTable, TopLevelClass topLevelClass) {

		Map<String, Object> local = Maps.newLinkedHashMap();
		local.put("todo", new ClassWrapper(introspectedTable));
		local.put("tags", "");

		String classComment = CodetemplatesLoader.getInstance().get("typecomment_context");
		classComment = VelocityUtils.evaluate(classComment, local);
		try {
			List<String> lines = CharStreams.readLines(CharStreams.newReaderSupplier(classComment));
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
				topLevelClass.addJavaDocLine(line);
				isfirst = false;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 生成字段注释.
	 *
	 * @param field the field
	 * @param topLevelClass the top level class
	 * @param introspectedColumn the introspected column
	 * @param introspectedTable the introspected table
	 * @param modelClassType the model class type
	 */
	public void fieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		String remarks = introspectedColumn.getRemarks();
		if (!Strings.isNullOrEmpty(remarks)) {
			field.addJavaDocLine("/** " + remarks + " */");
		}
	}

	/**
	 * 生成方法注释.
	 *
	 * @param method the method
	 * @param interfaze the interfaze
	 * @param introspectedTable the introspected table
	 * @param body the body
	 */
	public void methodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable, String body) {

		Map<String, Object> local = Maps.newLinkedHashMap();
		local.put("todo", (Strings.isNullOrEmpty(body) ? "TODO" : body));
		local.put("tags", new MethodWrapper(method));

		String methodComment = CodetemplatesLoader.getInstance().get("methodcomment_context");
		methodComment = VelocityUtils.evaluate(methodComment, local);
		try {
			List<String> lines = CharStreams.readLines(CharStreams.newReaderSupplier(methodComment));
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
				method.addJavaDocLine(line);
				isfirst = false;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		classGenerated(introspectedTable, topLevelClass);
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		classGenerated(introspectedTable, topLevelClass);
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		classGenerated(introspectedTable, topLevelClass);
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		classGenerated(introspectedTable, interfaze);
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
		methodGenerated(method, interfaze, introspectedTable, "");
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
		methodGenerated(method, interfaze, introspectedTable, "");
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
		methodGenerated(method, interfaze, introspectedTable, "");
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
		methodGenerated(method, interfaze, introspectedTable, "");
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
		methodGenerated(method, interfaze, introspectedTable, "");
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
		methodGenerated(method, interfaze, introspectedTable, "");
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
		methodGenerated(method, interfaze, introspectedTable, "");
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean validate(List<String> warnings) {

		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		fieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
		return true;
	}

}
