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

import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;
import org.hlc.demo.mybatis.utils.Log;

import com.google.common.collect.Maps;

/**
 * TODO.
 *
 * @author huanglicong
 * @version V1.0
 */
public class TemplateVisitor extends VisitorSupport {

	private Map<String, String> templates = Maps.newLinkedHashMap();
	private Element temp;

	/** {@inheritDoc} */
	@Override
	public void visit(Element element) {

		temp = null;// clean
		String name = element.getName();
		if ("template".equals(name)) {
			temp = element;
		}
		Log.print("解析codetemplates->" + name);
	}

	/** {@inheritDoc} */
	public void visit(Attribute attr) {
		if (temp == null) {
			return;
		}
		String name = attr.getName();
		if ("context".equals(name)) {
			templates.put(attr.getText(), temp.getText());
		}
		Log.print("解析codetemplates->" + temp.getName() + "->" + name);
	}

	public Map<String, String> getTemplates() {
		return templates;
	}
}
