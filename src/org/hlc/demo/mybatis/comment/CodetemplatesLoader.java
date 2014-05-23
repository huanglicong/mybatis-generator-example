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

import java.net.URL;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.google.common.collect.Maps;
import com.google.common.io.Resources;

/**
 * TODO.
 *
 * @author huanglicong
 * @version V1.0
 */
public class CodetemplatesLoader {

	private static CodetemplatesLoader instance;

	private Map<String, String> templates = Maps.newLinkedHashMap();

	public static CodetemplatesLoader getInstance() {
		if (instance == null) {
			instance = new CodetemplatesLoader();
			try {
				instance.loadAndResolveDocument();
			} catch (DocumentException e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
	}

	private void loadAndResolveDocument() throws DocumentException {
		URL url = Resources.getResource("codetemplates.xml");
		SAXReader reader = new SAXReader();
		Document document = reader.read(url.getFile());
		Element root = document.getRootElement();
		TemplateVisitor visitor = new TemplateVisitor();
		root.accept(visitor);
		templates.putAll(visitor.getTemplates());
	}

	public String get(Object key) {
		return templates.get(key);
	}

}
